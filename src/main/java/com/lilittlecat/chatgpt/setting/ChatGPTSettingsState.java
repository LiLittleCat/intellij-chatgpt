package com.lilittlecat.chatgpt.setting;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiLittleCat
 * @link <a href="https://github.com/LiLittleCat">https://github.com/LiLittleCat</a>
 * @since 2022/12/10
 */
@State(
        name = "com.lilittlecat.chatgpt.setting.ChatGPTSettingsState",
        storages = @Storage("ChatGPTSettingsState.xml")
)
public class ChatGPTSettingsState implements PersistentStateComponent<ChatGPTSettingsState> {
    public String sessionToken = "";

    public String defaultUrl = "";

    public List<String> urlList = new ArrayList<>();

    @Nullable
    @Override
    public ChatGPTSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull ChatGPTSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public static ChatGPTSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(ChatGPTSettingsState.class);
    }
}
