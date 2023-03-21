<img src="src/main/resources/META-INF/pluginIcon.svg" align="right" width="128" height="128" alt="icon"/>

# ChatGPT Tool

![Build](https://github.com/LiLittleCat/intellij-chatgpt/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/20629-chatgpt-tool.svg)](https://plugins.jetbrains.com/plugin/20629-chatgpt-tool)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/20629-chatgpt-tool.svg)](https://plugins.jetbrains.com/plugin/20629-chatgpt-tool)

[English](README.md)

一个将 [ChatGPT](https://chat.openai.com/) 和 [其他第三方镜像网站](https://github.com/LiLittleCat/awesome-free-chatgpt) 整合到 JetBrains IDEs 的插件。

## 功能

- 🚀 易于使用，无需离开 IDE 即可与 ChatGPT 交互。
- 🆓 集成免费的第三方镜像网站，并且更新方便。
- ✅ 添加和管理多个 ChatGPT URL。
- 🔄 轻松在不同的 ChatGPT URL 之间切换。

## 如何工作的

此插件使用 JCEF（Java Chromium Embedded Framework）来渲染内容。它是一个轻量级且跨平台的基于 Chromium 的网络浏览器引擎，被 IntelliJ IDEA 用于渲染 IDE 的 HTML 内容。
JCEF 支持 IntelliJ IDEA 2020.2 及更高版本。有关更多信息，请参阅 [JCEF](https://plugins.jetbrains.com/docs/intellij/jcef.html)。

## 安装

- 使用 IDE 内部插件系统：

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd> 搜索 "ChatGPT Tool"</kbd> >
  <kbd>Install Plugin</kbd>

- 手动安装：

  下载 [最新版本](https://github.com/LiLittleCat/intellij-chatgpt/releases/latest) 找到
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>
  手动安装。

## 使用方法

1. 打开 "ChatGPT Tool" 工具窗口，它将打开默认的 ChatGPT URL。
   ![](/image/login.png)
2. 如果您将 [官方 ChatGPT](https://chat.openai.com/) 设置为默认，您可以注册一个 [OpenAI](https://beta.openai.com/signup) 账户并登录。
   ![](/image/use.png)
3. 打开设置以更改默认的 ChatGPT URL 或添加更多 URL。
   ![](/image/settings-info.png)
   说明:
    1. 快速打开设置。
    2. 刷新已打开的页面。
    3. 向工具窗口添加新的 URL。
    4. 在浏览器中打开作者的 GitHub 页面。
    5. 在浏览器中打开作者的其他插件。
    6. 选择默认 URL，当您首次打开工具窗口和关闭所有已打开的页面时，将打开默认 URL。
    7. URL 列表，您可以添加、编辑和删除 URL 列表。
    8. 添加新的 URL。
       ![](/image/add-tab.png)
    9. 删除选定的 URL。
    10. 从 [作者的另一个 GitHub 仓库](https://github.com/LiLittleCat/awesome-free-chatgpt) 获取 URL 列表。
4. 使用第三方镜像网站。
   ![](/image/another.png)

> **注意**
>
> 无法使用 Google 账户或 Microsoft 账户登录官方 ChatGPT 不是由于此插件造成的，而是由官方 ChatGPT 本身造成的。
>
> 有关更多信息，请参阅 https://github.com/LiLittleCat/intellij-chatgpt/issues/7 和 https://github.com/JetBrains/jcef/issues/14 。
