package com.lilittlecat.chatgpt.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.lilittlecat.chatgpt.message.ChatGPTBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Objects;

/**
 * @author <a href="https://github.com/LiLittleCat">LiLittleCat</a>
 * @since 2023/3/9
 */
public class AddTabAction extends DumbAwareAction {
    public AddTabAction(@NotNull @Nls String text) {
        super(() -> text, AllIcons.General.Add);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String[] options = new String[] {"Content 1", "Content 2", "Content 3"};
//        String selected = Messages.showChooseDialog("Select Content", "Choose Content to Add", options, options[0], null);
        String selected = Messages.showEditableChooseDialog("Select Content", "Choose Content to Add", AllIcons.General.Add, options, options[0], null);
        if (selected == null) {
            return;
        }
        ToolWindowManager instance = ToolWindowManager.getInstance(Objects.requireNonNull(e.getProject()));
        ToolWindow toolWindow = instance.getToolWindow(ChatGPTBundle.message("name"));
        assert toolWindow != null;
        ContentManager contentManager = toolWindow.getContentManager();
        switch (selected) {
            case "Content 1": {
                Content content = contentManager.getFactory().createContent(new JLabel("Content 1"), "Content 1", true);
                contentManager.addContent(content);
                break;
            }
            case "Content 2": {
                Content content = contentManager.getFactory().createContent(new JLabel("Content 2"), "Content 2", true);
                contentManager.addContent(content);
                break;
            }
            case "Content 3": {
                Content content = contentManager.getFactory().createContent(new JLabel("Content 3"), "Content 3", true);
                contentManager.addContent(content);
                break;
            }
        }
    }
}
