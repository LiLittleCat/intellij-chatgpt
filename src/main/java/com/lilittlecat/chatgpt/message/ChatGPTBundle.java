package com.lilittlecat.chatgpt.message;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.util.function.Supplier;

/**
 * @author <a href="https://github.com/LiLittleCat">LiLittleCat</a>
 * @since 2022/12/10
 */
public class ChatGPTBundle extends DynamicBundle {
    @NonNls
    public static final String BUNDLE = "messages.ChatGPTBundle";
    private static final ChatGPTBundle INSTANCE = new ChatGPTBundle();

    private ChatGPTBundle() {
        super(BUNDLE);
    }

    @NotNull
    public static @Nls String message(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key, Object @NotNull ... params) {
        return INSTANCE.getMessage(key, params);
    }

    @NotNull
    public static Supplier<@Nls String> messagePointer(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key, Object @NotNull ... params) {
        return INSTANCE.getLazyMessage(key, params);
    }
}
