package com.lilittlecat.chatgpt.action;

import com.intellij.icons.AllIcons;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.lilittlecat.chatgpt.message.ChatGPTBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a href="https://github.com/LiLittleCat">LiLittleCat</a>
 * @since 2023/3/21
 */
public class GitHubAction extends DumbAwareAction {

    public GitHubAction(@NotNull @Nls String text) {
        super(() -> text, AllIcons.Vcs.Vendors.Github);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        BrowserUtil.browse(ChatGPTBundle.message("github.url"));
    }
}
