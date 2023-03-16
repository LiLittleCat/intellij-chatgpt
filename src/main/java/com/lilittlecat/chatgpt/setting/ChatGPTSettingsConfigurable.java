package com.lilittlecat.chatgpt.setting;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.cellvalidators.StatefulValidatingCellEditor;
import com.intellij.openapi.ui.cellvalidators.ValidatingTableCellRendererWrapper;
import com.intellij.openapi.ui.cellvalidators.ValidationUtils;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.ColoredTableCellRenderer;
import com.intellij.ui.SeparatorComponent;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.fields.ExtendableTextField;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.JBInsets;
import com.intellij.util.ui.ListTableModel;
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

    private Disposable myDisposable = Disposer.newDisposable();

    private JPanel myMainPanel;

    private JPanel defaultUrlBorderBox;
    private JComboBox<String> defaultUrlComboBox;

    private JPanel urlListBorderBox;

    private ListTableModel<String> myModel = new ListTableModel<>() {
        @Override
        public void addRow() {
            addRow("");
        }
    };

    private JBTable myTable;


    // A default constructor with no arguments is required because this implementation
    // is registered as an applicationConfigurable EP
    public ChatGPTSettingsConfigurable() {
        init();
    }

    private JComponent init() {
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
        createComboBox();
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Default website: "), defaultUrlComboBox, 1, false)
                .addLabeledComponent(new SeparatorComponent(), new JPanel(), 1, false)
                .addLabeledComponent(new JBLabel("Website list: "), table, 3, true)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
        return myMainPanel;
    }

    private void createComboBox() {
        // get value from myTable dynamically and set first value in myTable as default value of comboBox
        defaultUrlComboBox = new ComboBox<>();
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
                }

                List<String> items = new ArrayList<>(myModel.getItems());
                items.set(row, value);
                myModel.setItems(items);
                defaultUrlComboBox.addItem(value);
                myModel.fireTableCellUpdated(row, TableModelEvent.ALL_COLUMNS);
                myTable.repaint();
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

        return ToolbarDecorator.createDecorator(myTable)
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
        return !instance.urlList.equals(myModel.getItems()) || !instance.defaultUrl.equals(defaultUrlComboBox.getSelectedItem());
    }

    @Override
    public void apply() {
        myTable.editingStopped(null);
        ChatGPTSettingsState instance = ChatGPTSettingsState.getInstance();
        instance.defaultUrl = Objects.requireNonNull(defaultUrlComboBox.getSelectedItem()).toString();
        instance.urlList = myModel.getItems();
//        instance.urlList.addAll(myModel.getItems());
    }

    @Override
    public void reset() {
        ChatGPTSettingsState instance = ChatGPTSettingsState.getInstance();
        myModel.setItems(instance.urlList);
        defaultUrlComboBox.setSelectedItem(instance.defaultUrl);
    }

    @Override
    public void disposeUIResources() {
        Disposer.dispose(myDisposable);
    }

    @Override
    public @NotNull String getId() {
        return "com.lilittlecat.chatgpt.setting.ChatGPTSettingConfigurable";
    }
}
