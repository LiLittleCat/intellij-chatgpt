package com.lilittlecat.chatgpt.setting;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.cellvalidators.StatefulValidatingCellEditor;
import com.intellij.openapi.ui.cellvalidators.ValidatingTableCellRendererWrapper;
import com.intellij.openapi.ui.cellvalidators.ValidationUtils;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.*;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.ui.components.fields.ExtendableTextField;
import com.intellij.ui.table.JBTable;
import com.intellij.util.messages.MessageBusConnection;
import com.intellij.util.ui.*;
import com.lilittlecat.chatgpt.action.FetchURLAction;
import com.lilittlecat.chatgpt.message.ChatGPTBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author LiLittleCat
 * @link <a href="https://github.com/LiLittleCat">https://github.com/LiLittleCat</a>
 * @since 2022/12/10
 */
public class ChatGPTSettingsConfigurable implements SearchableConfigurable {

    private MessageBusConnection connection;

    // A default constructor with no arguments is required because this implementation
    // is registered as an applicationConfigurable EP
    public ChatGPTSettingsConfigurable() {
        connection = ApplicationManager.getApplication().getMessageBus().connect();
        connection.subscribe(UpdateChatGPTSettingStateTopic.TOPIC, this::refreshUI);
        init();
    }

    private void refreshUI() {
        // Update the defaultUrlComboBox
        defaultUrlComboBox.removeAllItems();
        List<String> items = ChatGPTSettingsState.getInstance().urlList;
        for (String item : items) {
            defaultUrlComboBox.addItem(item);
        }
        defaultUrlComboBox.setSelectedItem(ChatGPTSettingsState.getInstance().defaultUrl);

        // Update the myModel
        myModel.setItems(ChatGPTSettingsState.getInstance().urlList);
    }

    @Override
    public void disposeUIResources() {
        // Disconnect the message bus connection when the UI is disposed
        connection.disconnect();
        Disposer.dispose(myDisposable);
    }

    @Override
    public @NotNull String getId() {
        return "com.lilittlecat.chatgpt.setting.ChatGPTSettingConfigurable";
    }

    public void addUrl(@NotNull String url) {
        if (StringUtil.isNotEmpty(url)) {
            defaultUrlComboBox.addItem(url);
            myModel.addRow(url);
        }
    }

    private Disposable myDisposable = Disposer.newDisposable();

    private JPanel myMainPanel;
    private JBTextArea sessionToken = new JBTextArea();
    private JComboBox<String> defaultUrlComboBox;

    private ListTableModel<String> myModel;

    private JBTable myTable;

    private JComponent init() {
        myModel = new ListTableModel<>() {
            @Override
            public void addRow() {
                // add item to table
                addRow("");
            }

            @Override
            public void removeRow(int idx) {
                String item = getItem(idx);
                if (item.equals(ChatGPTSettingsState.getInstance().defaultUrl)) {
                    // popup a dialog to tell user that the default url can't be removed
                    JOptionPane.showMessageDialog(null, ChatGPTBundle.message("chatgpt.settings.defaultUrlCanNotBeRemoved"));
                } else if (item.equals(ChatGPTBundle.message("original.url"))) {
                    // popup a dialog to tell user that the original url can't be removed
                    JOptionPane.showMessageDialog(null, ChatGPTBundle.message("chatgpt.settings.originalUrlCanNotBeRemoved"));
                } else {
                    // remove item from table
                    super.removeRow(idx);
                    // remove item from comboBox
                    defaultUrlComboBox.removeItemAt(idx);
                }
            }

            @NotNull
            @Override
            public List<String> getItems() {
                List<String> items = super.getItems();
                // change Collections.unmodifiableList to ArrayList
                return new ArrayList<>(items);
            }

        };

        // init table
        myModel.addRows(ChatGPTSettingsState.getInstance().urlList);
        myTable = new JBTable(myModel) {
            @Override
            public void editingCanceled(ChangeEvent e) {
                int row = getEditingRow();
                super.editingCanceled(e);
                if (row >= 0 && row < myModel.getRowCount() && StringUtil.isEmpty(myModel.getRowValue(row))) {
                    myModel.removeRow(row);
                }
            }
        };
        JComponent table = createTable();
        table.setPreferredSize(new Dimension(500, 200));
        createComboBox();

        sessionToken.setFont(UIUtil.getLabelFont());
        sessionToken.setLineWrap(true);
        JBScrollPane scrollPane = new JBScrollPane(sessionToken,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel(ChatGPTBundle.message("setting.session.token.label")), scrollPane, 1, true)
                .addLabeledComponent(new JBLabel(ChatGPTBundle.message("default.url.message")), defaultUrlComboBox, 1, false)
                .addLabeledComponent(new SeparatorComponent(), new JPanel(), 1, false)
                .addLabeledComponent(new JBLabel(ChatGPTBundle.message("url.list.message")), table, 3, true)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
        return myMainPanel;
    }

    private void createComboBox() {
        // get value from myTable dynamically and set first value in myTable as default value of comboBox
        defaultUrlComboBox = new ComboBox<>();
        Dimension preferredSize = defaultUrlComboBox.getPreferredSize();
        preferredSize.width = 200; // Set the desired width here
        defaultUrlComboBox.setPreferredSize(preferredSize);
        List<String> items = myModel.getItems();
        for (String item : items) {
            defaultUrlComboBox.addItem(item);
        }
        defaultUrlComboBox.setSelectedItem(ChatGPTSettingsState.getInstance().defaultUrl);
    }

    private JComponent createTable() {

        myModel.setColumnInfos(new ColumnInfo[]{new ColumnInfo<String, String>("") {

            @Override
            public @Nullable String valueOf(String s) {
                return s;
            }

            @Override
            public boolean isCellEditable(String s) {
                return true;
            }

            @Override
            public void setValue(String s, String value) {
                int row = myTable.getSelectedRow();
                if (StringUtil.isEmpty(value) && row >= 0 && row < myModel.getRowCount()) {
                    myModel.removeRow(row);
                    defaultUrlComboBox.removeItemAt(row);
                }
                List<String> items = new ArrayList<>(myModel.getItems());
                items.set(row, value);
                myModel.setItems(items);
                myModel.fireTableCellUpdated(row, TableModelEvent.ALL_COLUMNS);
                myTable.repaint();
                // add to comboBox
                defaultUrlComboBox.addItem(value);
            }
        }});
        myTable.getColumnModel().setColumnMargin(0);
        myTable.setShowColumns(false);
        myTable.setShowGrid(false);
        myTable.getEmptyText().setText("No websites added.");
        myTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myTable.setToolTipText("Double click to edit it");

        ExtendableTextField cellEditor = new ExtendableTextField();
        DefaultCellEditor editor = new StatefulValidatingCellEditor(cellEditor, myDisposable).
                withStateUpdater(vi -> ValidationUtils.setExtension(cellEditor, vi));
        editor.setClickCountToStart(2);
        myTable.setDefaultEditor(Object.class, editor);

        myTable.setDefaultRenderer(Object.class, new ValidatingTableCellRendererWrapper(new ColoredTableCellRenderer() {
            {
                setIpad(new JBInsets(0, 0, 0, 0));
            }

            @Override
            protected void customizeCellRenderer(@NotNull JTable table, @Nullable Object value, boolean selected, boolean hasFocus, int row, int column) {
                if (row >= 0 && row < myModel.getRowCount()) {
                    String prefix = myModel.getRowValue(row);
                    setForeground(selected ? table.getSelectionForeground() : table.getForeground());
                    setBackground(selected ? table.getSelectionBackground() : table.getBackground());
                    append(prefix, SimpleTextAttributes.REGULAR_ATTRIBUTES);
                    setToolTipText("Double click to edit it.");
                }
            }

            @Override
            protected SimpleTextAttributes modifyAttributes(SimpleTextAttributes attributes) {
                return attributes;
            }
        }).bindToEditorSize(cellEditor::getPreferredSize));

        AnActionButton fetchUrlListFromGithub = AnActionButton.fromAction(new FetchURLAction(ChatGPTBundle.message("chatgpt.settings.fetch.url.list.from.github")));
        return ToolbarDecorator.createDecorator(myTable)
                .addExtraAction(fetchUrlListFromGithub)
                .disableUpDownActions()
                .createPanel();
    }


    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return ChatGPTBundle.message("setting.menu.text");
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return defaultUrlComboBox;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return myMainPanel;
    }

    @Override
    public boolean isModified() {
        ChatGPTSettingsState instance = ChatGPTSettingsState.getInstance();
        boolean modified = false;
        modified = !instance.defaultUrl.equals(defaultUrlComboBox.getSelectedItem());
        List<String> urlList = instance.urlList;
        List<String> items = myModel.getItems();
        modified |= urlList.size() != items.size();
        modified |= !urlList.equals(items);
        modified |= !instance.sessionToken.equals(sessionToken.getText());
        return modified;
    }

    @Override
    public void apply() {
        myTable.editingStopped(null);
        ChatGPTSettingsState instance = ChatGPTSettingsState.getInstance();
        instance.defaultUrl = Objects.requireNonNull(defaultUrlComboBox.getSelectedItem()).toString();
        instance.urlList = myModel.getItems();
        instance.sessionToken = sessionToken.getText();
        // Refresh the UI after updating the settings
        refreshUI();
//        instance.urlList.addAll(myModel.getItems());
    }

    @Override
    public void reset() {
        ChatGPTSettingsState instance = ChatGPTSettingsState.getInstance();
        myModel.setItems(instance.urlList);
        defaultUrlComboBox.removeAllItems();
        for (String s : instance.urlList) {
            defaultUrlComboBox.addItem(s);
        }
        defaultUrlComboBox.setSelectedItem(instance.defaultUrl);
        sessionToken.setText(instance.sessionToken);
    }
}
