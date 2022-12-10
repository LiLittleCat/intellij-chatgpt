package com.lilittlecat.chatgpt.window;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.lilittlecat.chatgpt.action.SettingsAction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiLittleCat
 * @link <a href="https://github.com/LiLittleCat">https://github.com/LiLittleCat</a>
 * @since 2022/12/10
 */
public class ChatGPTToolWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentManager contentManager = toolWindow.getContentManager();
        Content labelContent = contentManager.getFactory().createContent(
                new ChatGPTToolWindow().getContent(), null, false);
        contentManager.addContent(labelContent);
        // add actions to tool window
        List<AnAction> anActionList = new ArrayList<>();
        anActionList.add(new SettingsAction("Settings"));
        toolWindow.setTitleActions(anActionList);
    }
}
