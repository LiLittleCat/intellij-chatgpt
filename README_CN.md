<img src="src/main/resources/META-INF/pluginIcon.svg" align="right" width="128" height="128" alt="icon"/>

# ChatGPT

![Build](https://github.com/LiLittleCat/intellij-chatgpt/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)

一个适合所有 JetBrains IDE 的 [ChatGPT](https://chat.openai.com/) 插件。

## 特点

🚀 简单快捷，无需烦人的 token 复制，只需登录一次就可以使用。

## 安装

- 使用 IDE 内部插件系统：

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd> 搜索 "ChatGPT Tool"</kbd> >
  <kbd>Install Plugin</kbd>

- 手动安装：

  下载 [最新版本](https://github.com/LiLittleCat/intellij-chatgpt/releases/latest) 找到
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>
  手动安装。

## 使用

1. 注册一个 [OpenAI](https://beta.openai.com/signup)
   账户。注册教程：https://mirror.xyz/boxchen.eth/9O9CSqyKDj4BKUIil7NC1Sa1LJM-3hsPqaeW_QjfFBc
2. 打开工具窗口 "ChatGPT", 第一次使用时需要登录:
   ![](/image/login.png)
3. 输入你想问的问题：
   ![](/image/use.png)

> **Note**
> 登录后，session token 会被保存在设置中。在第一次使用时，你可以设置一个有效的 session token ，这样就不需要登录了。
> 也可以复制这个 session token 用于其他用途，比如使用其他需要 session token 的 ChatGPT 客户端。
> ![](/image/settings.png)