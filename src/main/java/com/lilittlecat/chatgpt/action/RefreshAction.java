package com.lilittlecat.chatgpt.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import com.lilittlecat.chatgpt.message.ChatGPTBundle;
import com.lilittlecat.chatgpt.window.ChatGPTToolWindow;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author LiLittleCat
 * @link <a href="https://github.com/LiLittleCat">https://github.com/LiLittleCat</a>
 * @since 2022/12/15
 */
public class RefreshAction extends DumbAwareAction {
    public RefreshAction(@NotNull @Nls String text) {
        super(() -> text, AllIcons.Actions.Refresh);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // Get the ToolWindowManager instance for the current project
        ToolWindowManager instance = ToolWindowManager.getInstance(Objects.requireNonNull(e.getProject()));
        // Get the tool window with the specified name
        ToolWindow toolWindow = instance.getToolWindow(ChatGPTBundle.message("name"));
        if (toolWindow != null) {
            // Get the content manager for the tool window
            ContentManager contentManager = toolWindow.getContentManager();
            // Get the currently selected content
            Content selectedContent = contentManager.getSelectedContent();
            assert selectedContent != null;
            // Get the tab name of the selected content
            String selectedContentTabName = selectedContent.getTabName();
            // Store the index of the selected content
            int selectedIndex = contentManager.getIndexOfContent(selectedContent);
            // Remove the selected content
            contentManager.removeContent(selectedContent, true);
            // Iterate through all contents in the content manager
            for (int i = 0; i < contentManager.getContentCount(); i++) {
                // Get the current content
                Content content = contentManager.getContent(i);
                assert content != null;
                // Get the tab name of the current content
                String tabName = content.getTabName();
                // Remove the current content
                contentManager.removeContent(content, true);
                // Create a new content with the same tab name
                Content browser = ContentFactory.SERVICE.getInstance().createContent(
                        new ChatGPTToolWindow(tabName).getContent(), tabName, false);
                // Add the new content to the content manager
                contentManager.addContent(browser);
            }
            // Set the new content as the selected content using the updated selectedIndex
            Content selectedBrowser = ContentFactory.SERVICE.getInstance().createContent(
                    new ChatGPTToolWindow(selectedContentTabName).getContent(), selectedContentTabName,false);
            contentManager.addContent(selectedBrowser, selectedIndex);
            contentManager.setSelectedContent(selectedBrowser);
        }
    }

}
