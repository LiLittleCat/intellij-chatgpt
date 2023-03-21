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
import com.lilittlecat.chatgpt.setting.ChatGPTSettingsState;
import com.lilittlecat.chatgpt.window.ChatGPTToolWindow;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * add new tab action
 *
 * @author <a href="https://github.com/LiLittleCat">LiLittleCat</a>
 * @since 2023/3/9
 */
public class AddTabAction extends DumbAwareAction {
    public AddTabAction(@NotNull @Nls String text) {
        super(() -> text, AllIcons.General.Add);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        List<String> urlList = ChatGPTSettingsState.getInstance().urlList;
        String[] options = urlList.toArray(new String[0]);
//        String selected = Messages.showChooseDialog("Select Content", "Choose Content to Add", options, options[0], null);
        String selected = Messages.showEditableChooseDialog("Select URL", "Choose a URL to Add", AllIcons.General.Add, options, options[0], null);
        if (selected == null) {
            return;
        }
        ToolWindowManager instance = ToolWindowManager.getInstance(Objects.requireNonNull(e.getProject()));
        ToolWindow toolWindow = instance.getToolWindow(ChatGPTBundle.message("name"));
        assert toolWindow != null;
        ContentManager contentManager = toolWindow.getContentManager();
        Content content = contentManager.getFactory().createContent(new ChatGPTToolWindow(selected).getContent(), selected, false);
        contentManager.addContent(content);
        contentManager.setSelectedContent(content);
    }
}
