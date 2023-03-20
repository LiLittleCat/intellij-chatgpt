package com.lilittlecat.chatgpt.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.lilittlecat.chatgpt.message.ChatGPTBundle;
import com.lilittlecat.chatgpt.setting.ChatGPTSettingsState;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/LiLittleCat">LiLittleCat</a>
 * @since 2023/3/20
 */
public class FetchURLAction extends DumbAwareAction {
    public FetchURLAction(@NotNull @Nls String text) {
        super(() -> text, AllIcons.Actions.CheckOut);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .build();

        String primaryUrl = ChatGPTBundle.message("free.chatgpt.file.url.original");
        String fallbackUrl = ChatGPTBundle.message("free.chatgpt.file.url.fallback");

        try {
            JSONArray jsonArray = fetchJsonArray(client, primaryUrl);
            processJsonArray(jsonArray);
        } catch (IOException e) {
            try {
                JSONArray jsonArray = fetchJsonArray(client, fallbackUrl);
                processJsonArray(jsonArray);
            } catch (IOException e2) {
                JOptionPane.showMessageDialog(null, ChatGPTBundle.message("cannot.fetch.message"), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private static JSONArray fetchJsonArray(OkHttpClient client, String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            assert response.body() != null;
            String jsonData = response.body().string();
            return new JSONArray(jsonData);
        }
    }

    private static void processJsonArray(JSONArray jsonArray) {
        List<String> urlList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            String url = jsonArray.getString(i);
            ChatGPTSettingsState.getInstance().urlList.add(url);
        }
        // Process the List<String> here
        System.out.println(urlList);
    }

}
