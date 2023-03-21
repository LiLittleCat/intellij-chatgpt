package com.lilittlecat.chatgpt.window;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.content.ContentManagerEvent;
import com.intellij.ui.content.ContentManagerListener;
import com.lilittlecat.chatgpt.action.AddTabAction;
import com.lilittlecat.chatgpt.action.RefreshAction;
import com.lilittlecat.chatgpt.action.SettingsAction;
import com.lilittlecat.chatgpt.setting.ChatGPTSettingsState;
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
        @SuppressWarnings("DialogTitleCapitalization")
        Content labelContent = contentManager.getFactory().createContent(
                new ChatGPTToolWindow(ChatGPTSettingsState.getInstance().defaultUrl).getContent(),
                ChatGPTSettingsState.getInstance().defaultUrl, false);
        contentManager.addContent(labelContent);
        contentManager.addContentManagerListener(
                new ContentManagerListener() {
                    @Override
                    public void contentRemoved(@NotNull ContentManagerEvent event) {
                        // when all tabs are closed, add a new tab
                        if (contentManager.getContentCount() == 0) {
                            Content labelContent = contentManager.getFactory().createContent(
                                    new ChatGPTToolWindow(ChatGPTSettingsState.getInstance().defaultUrl).getContent(),
                                    ChatGPTSettingsState.getInstance().defaultUrl, false);
                            contentManager.addContent(labelContent);
                        }
                    }
                }
        );
        // add actions to tool window
        List<AnAction> anActionList = new ArrayList<>();
        anActionList.add(new SettingsAction("Settings"));
        anActionList.add(new RefreshAction("Refresh"));
        anActionList.add(new AddTabAction("Add Tab"));
        toolWindow.setTitleActions(anActionList);
    }
}
