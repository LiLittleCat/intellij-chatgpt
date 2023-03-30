package com.lilittlecat.chatgpt.setting;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.lilittlecat.chatgpt.message.ChatGPTBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

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

    public String defaultUrl = ChatGPTBundle.message("default.url");

    public List<String> urlList;

    public ChatGPTSettingsState() {
        urlList = new ArrayList<>();
        urlList.add(ChatGPTBundle.message("default.url"));
//        ResourceBundle bundle = ResourceBundle.getBundle(ChatGPTBundle.BUNDLE);
//        Enumeration<String> keys = bundle.getKeys();
//        while (keys.hasMoreElements()) {
//            String key = keys.nextElement();
//            if (key.startsWith("url")) {
//                String url = bundle.getString(key);
//                System.out.println(key + ": " + url);
//                urlList.add(url);
//            }
//        }
    }

    @Nullable
    @Override
    public ChatGPTSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull ChatGPTSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

//    public void setSessionToken(String sessionToken) {
//        this.sessionToken = sessionToken;
//    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public static ChatGPTSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(ChatGPTSettingsState.class);
    }
}
