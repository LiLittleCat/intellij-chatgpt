package com.lilittlecat.chatgpt.setting;

import com.intellij.openapi.options.SearchableConfigurable;
import com.lilittlecat.chatgpt.message.ChatGPTBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author LiLittleCat
 * @link <a href="https://github.com/LiLittleCat">https://github.com/LiLittleCat</a>
 * @since 2022/12/10
 */
public class ChatGPTSettingsConfigurable implements SearchableConfigurable {
    private ChatGPTSettingsComponent settingComponent;

    // A default constructor with no arguments is required because this implementation
    // is registered as an applicationConfigurable EP

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return ChatGPTBundle.message("setting.menu.text");
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return settingComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settingComponent = new ChatGPTSettingsComponent();
        return settingComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        ChatGPTSettingsState settings = ChatGPTSettingsState.getInstance();
        return !settingComponent.getSessionToken().equals(settings.sessionToken);
    }

    @Override
    public void apply() {
        ChatGPTSettingsState settings = ChatGPTSettingsState.getInstance();
        settings.sessionToken = settingComponent.getSessionToken();
    }

    @Override
    public void reset() {
        ChatGPTSettingsState settings = ChatGPTSettingsState.getInstance();
        settingComponent.setSessionToken(settings.sessionToken);
    }

    @Override
    public void disposeUIResources() {
        settingComponent = null;
    }

    @NotNull
    @Override
    public String getId() {
        return "com.lilittlecat.chatgpt.setting.ChatGPTSettingConfigurable";
    }

}
