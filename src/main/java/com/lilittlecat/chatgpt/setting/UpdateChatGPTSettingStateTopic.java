package com.lilittlecat.chatgpt.setting;

import com.intellij.util.messages.Topic;

/**
 * A topic for changing the state of the settings.
 *
 * @author LiLittleCat
 * @link <a href="https://github.com/LiLittleCat">https://github.com/LiLittleCat</a>
 * @since 2023/3/21
 */
public interface UpdateChatGPTSettingStateTopic {
    Topic<UpdateChatGPTSettingStateTopic> TOPIC = Topic.create("Update Field Topic", UpdateChatGPTSettingStateTopic.class);

    void stateChanged();
}
