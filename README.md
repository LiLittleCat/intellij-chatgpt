<img src="src/main/resources/META-INF/pluginIcon.svg" align="right" width="128" height="128" alt="icon"/>

# ChatGPT Tool

![Build](https://github.com/LiLittleCat/intellij-chatgpt/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/20629-chatgpt-tool.svg)](https://plugins.jetbrains.com/plugin/20629-chatgpt-tool)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/20629-chatgpt-tool.svg)](https://plugins.jetbrains.com/plugin/20629-chatgpt-tool)

[中文说明](README_CN.md)

This Jetbrains IDEs plugin integrates [ChatGPT](https://chat.openai.com/) and [other third-party mirror websites](https://github.com/LiLittleCat/awesome-free-chatgpt) of ChatGPT into JetBrains IDEs,
providing a seamless experience for developers to interact with the ChatGPT AI model directly within their development environment.

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

1. Open the tool window "ChatGPT Tool", it will open the default ChatGPT URL
   ![](/image/login.png)
2. If you use [official ChatGPT](https://chat.openai.com/) as default, you can register a [OpenAI](https://beta.openai.com/signup) account and log in.
   ![](/image/use.png)
3. Open the setting to change the default ChatGPT URL or add more URLs.
   ![](/image/settings-info.png)
   explanation:
    1. Open the setting quickly.
    2. Refresh the opened page.
    3. Add a new URL to the tool window.
    4. Open the author's GitHub page in Browser.
    5. Open other plugins from the author in Browser.
    6. Select the default URL, default URL will be opened when you first open the tool window and when you close all opened pages.
    7. URL list, you can add, edit, and delete the URL list.
    8. Add a new URL.
       ![](/image/add-tab.png)
    9. Delete the selected URL.
    10. Fetch the URL list from [the author's other GitHub repository](https://github.com/LiLittleCat/awesome-free-chatgpt).
4. Use third-party mirror websites.
   ![](/image/another.png)


> **Notice**
>
> Cannot log in to the official ChatGPT by Google account or Microsoft account is not caused by this plugin, but by the official ChatGPT itself.
>
> See https://github.com/LiLittleCat/intellij-chatgpt/issues/7 and https://github.com/JetBrains/jcef/issues/14 for more information.