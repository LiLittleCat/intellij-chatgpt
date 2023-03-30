<img src="src/main/resources/META-INF/pluginIcon.svg" align="right" width="128" height="128" alt="icon"/>

# ChatGPT Tool

![Build](https://github.com/LiLittleCat/intellij-chatgpt/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/20629-chatgpt-tool.svg)](https://plugins.jetbrains.com/plugin/20629-chatgpt-tool)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/20629-chatgpt-tool.svg)](https://plugins.jetbrains.com/plugin/20629-chatgpt-tool)

[中文说明](README_CN.md)

This Jetbrains IDEs plugin integrates [ChatGPT](https://chat.openai.com/) and [other third-party mirror websites](https://github.com/LiLittleCat/awesome-free-chatgpt) of ChatGPT into JetBrains IDEs,
providing a seamless experience for developers to interact with the ChatGPT AI model directly within their development environment.

> ### 💡 Notice
> Cannot log in to the official ChatGPT by Google account or Microsoft account is not caused by this plugin, but by the
> official ChatGPT itself.
>
> See https://github.com/LiLittleCat/intellij-chatgpt/issues/7 and https://github.com/JetBrains/jcef/issues/14 for more
> information.
>
> <details>
> <summary>💡 Click to expand the Solution for this</summary>
>
> 1. Go to [https://chat.openai.com/chat](https://chat.openai.com/chat) and log in or sign up.
> 2. Open dev tools.
> 3. Open `Application` > `Cookies`.
      [![pSSKdmR.png](https://s1.ax1x.com/2022/12/28/pSSKdmR.png)](https://imgse.com/i/pSSKdmR)
> 4. Copy the value for `__Secure-next-auth.session-token` as settings value.
      [![pSSK6pD.png](https://s1.ax1x.com/2022/12/28/pSSK6pD.png)](https://imgse.com/i/pSSK6pD)
> 5. After you enter the session token, you need to restart the IDE because ChatGPT Tool Windows needs a restart, and
     you won't need to log in until the session token is expired.
> </details>

## Feature

- 🚀 Easy to use, and interact with ChatGPT without leaving the IDE.
- 🆓 Integrates free third-party mirror websites and easily update.
- ✅ Add and manage multiple ChatGPT URLs.
- 🔄 Easily switch between different ChatGPT URLs.

## How it works

This plugin uses JCEF(Java Chromium Embedded Framework) to render the content. It is a lightweight and cross-platform web browser engine that is built on top of Chromium and is used by IntelliJ IDEA to render the HTML content of the IDE.
JCEF is supported in IntelliJ IDEA 2020.2 and later. See [JCEF](https://plugins.jetbrains.com/docs/intellij/jcef.html) for more information.

## Installation

- Using IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "ChatGPT Tool"</kbd> >
  <kbd>Install Plugin</kbd>

- Manually:

  Download the [latest release](https://github.com/LiLittleCat/intellij-chatgpt/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


## Usage

1. Open the "ChatGPT Tool" tool window, which will load the default ChatGPT URL.
   ![](/image/login.png)
2. If you have set the [official ChatGPT](https://chat.openai.com/) as the default, you can register for an [OpenAI](https://beta.openai.com/signup) account and log in.
   ![](/image/use.png)
3. Access the settings to modify the default ChatGPT URL or add additional URLs
   ![](/image/settings-info.png)

   Explanation:
    1. Quickly open the settings.
    2. Refresh the currently opened page.
    3. Add a new URL to the tool window.
    4. Open the author's GitHub page in browser.
    5. Open other plugins from the author in browser.
    6. Select the default URL. The default URL will be opened when you first launch the tool window and when all opened pages are closed.
    7. URL list: add, edit, and delete URLs.
    8. Add a new URL.
       ![](/image/add-tab.png)
    9. Delete the selected URL.
    10. Fetch the URL list from [the author's other GitHub repository](https://github.com/LiLittleCat/awesome-free-chatgpt).
4. Utilize third-party mirror websites.
   ![](/image/another.png)