package com.lilittlecat.chatgpt.setting;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.ListTableModel;
import com.intellij.util.ui.UIUtil;
import com.lilittlecat.chatgpt.message.ChatGPTBundle;

import javax.swing.*;

/**
 * @author LiLittleCat
 * @link <a href="https://github.com/LiLittleCat">https://github.com/LiLittleCat</a>
 * @since 2022/12/10
 */
public class ChatGPTSettingsComponent {
    private final JPanel myMainPanel;
    private final JBTextArea sessionToken = new JBTextArea();
    private final JBTextArea defaultUrl = new JBTextArea();
    private final JBTable urlTable = new JBTable();

    public ChatGPTSettingsComponent() {
        sessionToken.setFont(UIUtil.getLabelFont());
        sessionToken.setLineWrap(true);
        JBScrollPane scrollPane = new JBScrollPane(sessionToken,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        myMainPanel = FormBuilder.createFormBuilder()
//                .addLabeledComponent(new JBLabel(ChatGPTBundle.message("setting.session.token.label")), scrollPane, 1, false)
                .addLabeledComponent(new JBLabel(ChatGPTBundle.message("setting.session.token.label")), sessionToken, 1, false)
                .addLabeledComponent(new JBLabel("Table"), urlTable)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return sessionToken;
    }

    public String getSessionToken() {
        return sessionToken.getText();
    }

    public void setSessionToken(String token) {
        sessionToken.setText(token);
    }
}
