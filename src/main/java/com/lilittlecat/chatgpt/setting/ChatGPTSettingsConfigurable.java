package com.lilittlecat.chatgpt.setting;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.lilittlecat.chatgpt.message.ChatGPTBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiLittleCat
 * @link <a href="https://github.com/LiLittleCat">https://github.com/LiLittleCat</a>
 * @since 2022/12/10
 */
public class ChatGPTSettingsConfigurable implements SearchableConfigurable {
//    private ChatGPTSettingsComponent settingComponent;
//
//    // A default constructor with no arguments is required because this implementation
//    // is registered as an applicationConfigurable EP
//
//    @Nls(capitalization = Nls.Capitalization.Title)
//    @Override
//    public String getDisplayName() {
//        return ChatGPTBundle.message("setting.menu.text");
//    }
//
//    @Override
//    public JComponent getPreferredFocusedComponent() {
//        return settingComponent.getPreferredFocusedComponent();
//    }
//
//    @Nullable
//    @Override
//    public JComponent createComponent() {
//        settingComponent = new ChatGPTSettingsComponent();
//        return settingComponent.getPanel();
//    }
//
//    @Override
//    public boolean isModified() {
//        ChatGPTSettingsState settings = ChatGPTSettingsState.getInstance();
//        return !settingComponent.getSessionToken().equals(settings.sessionToken);
//    }
//
//    @Override
//    public void apply() {
//        ChatGPTSettingsState settings = ChatGPTSettingsState.getInstance();
//        settings.sessionToken = settingComponent.getSessionToken();
//    }
//
//    @Override
//    public void reset() {
//        ChatGPTSettingsState settings = ChatGPTSettingsState.getInstance();
//        settingComponent.setSessionToken(settings.sessionToken);
//    }
//
//    @Override
//    public void disposeUIResources() {
//        settingComponent = null;
//    }
//
//    @NotNull
//    @Override
//    public String getId() {
//        return "com.lilittlecat.chatgpt.setting.ChatGPTSettingConfigurable";
//    }

    private JTextField tokenField;
    private JComboBox<String> pathComboBox;
    private JPanel mainPanel;
    private List<String> paths;
    private JList<String> pathsList;
    private DefaultListModel<String> pathsListModel;

    public ChatGPTSettingsConfigurable() {
        paths = new ArrayList<>();
        pathsListModel = new DefaultListModel<>();
        pathsList.setModel(pathsListModel);
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "My Plugin Settings";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        mainPanel = new JPanel(new BorderLayout());

        JPanel tokenPanel = new JPanel(new GridLayout(1, 2));
        JLabel tokenLabel = new JLabel("Token:");
        tokenField = new JTextField();
        tokenPanel.add(tokenLabel);
        tokenPanel.add(tokenField);

        JPanel pathPanel = new JPanel(new GridLayout(1, 2));
        JLabel pathLabel = new JLabel("Default Path:");
        pathComboBox = new JComboBox<>();
        pathPanel.add(pathLabel);
        pathPanel.add(pathComboBox);

        JPanel pathsPanel = new JPanel(new BorderLayout());
        JLabel pathsLabel = new JLabel("Paths:");
        pathsList = new JBList<>();
        pathsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        JScrollPane pathsScrollPane = new JBScrollPane(pathsList);
        pathsScrollPane.setPreferredSize(new Dimension(200, 100));
        JButton addPathButton = new JButton("Add Path");
        JButton removePathButton = new JButton("Remove Path");
        addPathButton.addActionListener(e -> {
            String newPath = JOptionPane.showInputDialog(mainPanel, "Enter new path:");
            if (newPath != null && !newPath.trim().isEmpty()) {
                paths.add(newPath);
                pathsListModel.addElement(newPath);
            }
        });
        removePathButton.addActionListener(e -> {
            int index = pathsList.getSelectedIndex();
            if (index != -1) {
                paths.remove(index);
                pathsListModel.remove(index);
            }
        });
        JPanel pathsButtonPanel = new JPanel(new GridLayout(1, 2));
        pathsButtonPanel.add(addPathButton);
        pathsButtonPanel.add(removePathButton);
        pathsPanel.add(pathsLabel, BorderLayout.NORTH);
        pathsPanel.add(pathsScrollPane, BorderLayout.CENTER);
        pathsPanel.add(pathsButtonPanel, BorderLayout.SOUTH);

        mainPanel.add(tokenPanel, BorderLayout.NORTH);
        mainPanel.add(pathPanel, BorderLayout.CENTER);
        mainPanel.add(pathsPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        ChatGPTSettingsState settings = ChatGPTSettingsState.getInstance();
        // Save the settings to some storage
        String token = tokenField.getText();
        String defaultPath = (String) pathComboBox.getSelectedItem();
        // Save the paths list to some storage
        settings.setSessionToken(token);
        settings.setDefaultUrl(defaultPath);
        settings.setUrlList(paths);
    }

    @Override
    public void reset() {
        // Load the settings from some storage
        tokenField.setText("");
        pathComboBox.removeAllItems();
        // Load the paths list from some storage
    }

    @Override
    public void disposeUIResources() {
        mainPanel = null;
    }

    @Override
    public @NotNull String getId() {
        return "com.lilittlecat.chatgpt.setting.ChatGPTSettingConfigurable";
    }
}
