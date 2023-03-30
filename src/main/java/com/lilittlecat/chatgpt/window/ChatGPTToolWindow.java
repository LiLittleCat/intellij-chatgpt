package com.lilittlecat.chatgpt.window;


import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.ui.jcef.JBCefCookie;
import com.intellij.ui.jcef.JBCefCookieManager;
import com.lilittlecat.chatgpt.message.ChatGPTBundle;
import com.lilittlecat.chatgpt.setting.ChatGPTSettingsState;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author LiLittleCat
 * @link <a href="https://github.com/LiLittleCat">https://github.com/LiLittleCat</a>
 * @since 2022/12/10
 */
public class ChatGPTToolWindow extends SimpleToolWindowPanel {
    private static final Logger LOG = LoggerFactory.getLogger(ChatGPTToolWindow.class);

    private final JPanel content;

    private final String sessionTokenName = "__Secure-next-auth.session-token";

    ThreadPoolExecutor executor = new ThreadPoolExecutor(
            1, 10, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

    public ChatGPTToolWindow(String url) {
        super(true, false);
        this.content = new JPanel(new BorderLayout());
        if (!JBCefApp.isSupported()) {
            JEditorPane editorPane = new JEditorPane("text/html", ChatGPTBundle.message("jBCefApp.not.supported"));
            editorPane.setEditable(false);
            editorPane.setOpaque(false);
            editorPane.addHyperlinkListener(e -> {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    try {
                        java.awt.Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            JOptionPane.showMessageDialog(null, editorPane);
//            this.content.add(new JLabel(ChatGPTBundle.message("jBCefApp.not.supported"), SwingConstants.CENTER));
            this.content.add(editorPane, SwingConstants.CENTER);
            return;
        }
        JBCefBrowser jbCefBrowser = new JBCefBrowser();
        this.content.add(jbCefBrowser.getComponent(), BorderLayout.CENTER);
        if (url.equals(ChatGPTBundle.message("default.url"))) {
            setCookieForOfficalWebsite(url);
        }
        jbCefBrowser.loadURL(url);
    }

    private void setCookieForOfficalWebsite(String url) {
        JBCefCookieManager jbCefCookieManager = new JBCefCookieManager();
        // find if there is session token in settings
        ChatGPTSettingsState state = ChatGPTSettingsState.getInstance().getState();
        if (state != null) {
            String sessionToken = state.sessionToken;
            if (StringUtils.isNotBlank(sessionToken)) {
                // check the token is right or not

                // 2022.12.16 with Cloudflare, can not check token by http get

//                String cookie = sessionTokenName + "=" + sessionToken;
//                HttpGet httpGet = new HttpGet("https://chat.openai.com/chat");
//                httpGet.setHeader("Connection", "keep-alive");
//                httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.1 Safari/605.1.15");
//                httpGet.setHeader("Cookie", cookie);
//                httpGet.setHeader("Origin", "https://auth0.openai.com");
//                httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//                httpGet.setHeader("Accept-Language", "en-US,en;q=0.9");
//                try (CloseableHttpClient httpClient = HttpClients.createDefault();
//                     CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
//                    HttpEntity httpEntity = httpResponse.getEntity();
//                    String bodyString = EntityUtils.toString(httpEntity);
//                    if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK && !bodyString.contains("Welcome to ChatGPT")) {
                JBCefCookie jbCefCookie = new JBCefCookie(sessionTokenName, sessionToken, "chat.openai.com", "/", true, true);
                // use async set
                jbCefCookieManager.setCookie(ChatGPTBundle.message("default.url"), jbCefCookie, false);
//                    }
//                } catch (IOException e) {
//                    LOG.error("Error when check session token: ", e);
//                }
            }
        }
        // get session token after login, fill it in settings
        // fill it every login, in case invalid session token cause can't login problem
        executor.execute(() -> {
            String currentSessionToken = null;
            while (currentSessionToken == null) {
                List<JBCefCookie> cookies = jbCefCookieManager.getCookies();
                if (!cookies.isEmpty()) {
                    for (JBCefCookie cookie : cookies) {
                        if (cookie.getName().equals(sessionTokenName)) {
                            currentSessionToken = cookie.getValue();
                            ChatGPTSettingsState.getInstance().setSessionToken(currentSessionToken);
                        }
                    }
                }
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public JPanel getContent() {
        return content;
    }
}
