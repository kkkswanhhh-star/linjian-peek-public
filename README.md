---
title: 掌心窗 Hugging Face 版
emoji: 🪟
colorFrom: green
colorTo: blue
sdk: docker
app_port: 7860
pinned: false
---

# 掌心窗 Hugging Face Spaces 一体版

这是掌心窗 v0.1.8 的 **Hugging Face Spaces Docker 一体部署版**。

它把原来 Render 需要分别部署的两个服务合到一个 Space 里：

- 手机后端：`/health`、`/api/*`
- MCP 服务：`/mcp`、`/sse`

这样用户不需要在 Render 上建两个 Web Service，也不需要处理 Render 的绑卡弹窗。Hugging Face Spaces 的免费 CPU Basic 可以用于测试，但免费硬件闲置后会睡眠，重启后本地临时数据可能丢失。

## 快速部署

### 1. 新建 Space

打开 Hugging Face，创建一个新的 Space：

- Space name：随便取，例如 `palm-window`
- SDK：选择 `Docker`
- Hardware：保持默认 `CPU Basic / Free`
- Visibility：建议先选 `Private` 或 `Protected`，熟悉后再决定是否公开

### 2. 上传本包文件

把这个 ZIP 解压后，将里面所有文件上传到 Space 的 Files 页面。

也可以用 git 推送到 Space 仓库。

### 3. 设置密钥和变量

进入 Space 的 **Settings → Variables and secrets**。

添加 Secret：

```text
LINJIAN_TOKEN = 你自己生成的一串长密钥，建议 32 位以上
```

添加 Variable：

```text
LINJIAN_DEFAULT_DEVICE = my-phone
LINJIAN_KEEP = 3
```

不要把 `LINJIAN_TOKEN` 写进代码、README、评论区或截图里。

### 4. 等待构建完成

上传后 Hugging Face 会自动构建 Docker 镜像。构建完成后，打开你的 Space 地址：

```text
https://你的用户名-你的Space名.hf.space/health
```

看到类似下面内容就说明后端活了：

```json
{"ok": true, "service": "linjian-unified", "name": "掌心窗"}
```

MCP 健康检查地址：

```text
https://你的用户名-你的Space名.hf.space/mcp_health
```

ChatGPT / MCP 客户端连接地址：

```text
https://你的用户名-你的Space名.hf.space/mcp
```

旧 SSE 客户端连接地址：

```text
https://你的用户名-你的Space名.hf.space/sse
```

## 手机 App 怎么填

Android 掌心窗 App 里填：

```text
Server URL = https://你的用户名-你的Space名.hf.space
Token = 刚刚设置的 LINJIAN_TOKEN
Device ID = my-phone
```

然后点启动/保存，确认无障碍权限、通知权限、截图权限按 App 指引开启。

## 重要限制

1. 免费 Space 会睡眠，第一次访问可能需要等它醒来。
2. 这个版本默认把截图和状态存在容器本地目录里；Space 重启后，本地临时数据可能丢失。
3. 不要共用别人的 Space、Token、Device ID。每个人都应该部署自己的后端。
4. 如果手机 App 显示连不上，先打开 `/health` 看 Space 有没有醒。
5. 如果 MCP 能连但手机没反应，检查手机 App 的 Server URL、Token、Device ID 是否和 Space 一致。

## 路径说明

| 路径 | 用途 |
| --- | --- |
| `/health` | 手机后端健康检查 |
| `/api/poll` | 手机轮询命令 |
| `/api/screenshot` | 手机上传截图 |
| `/api/latest` | 读取最近截图 |
| `/api/device/state` | 手机状态上传/读取 |
| `/api/life_state` | 生活状态层 |
| `/mcp` | Streamable HTTP MCP |
| `/sse` | SSE MCP |
| `/mcp_health` | MCP 服务健康检查 |

## 本地测试（可选）

```bash
export LINJIAN_TOKEN="your-long-token"
docker build -t palm-window-hf .
docker run --rm -p 7860:7860 -e LINJIAN_TOKEN="$LINJIAN_TOKEN" palm-window-hf
```

然后打开：

```text
http://localhost:7860/health
http://localhost:7860/mcp_health
```
