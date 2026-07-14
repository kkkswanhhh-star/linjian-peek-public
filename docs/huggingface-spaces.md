# 掌心窗 Hugging Face Spaces 部署教程

这份教程给不想绑 Render 卡的用户使用。

## 为什么换 Hugging Face Spaces？

Render 有时即使选择 Free，也会要求账号绑卡验证。Hugging Face Spaces 的 CPU Basic 免费硬件可以用来测试轻量服务，所以我们做了这个 Docker 一体版。

原 Render 版需要两个服务：

- `server`：Python 手机后端
- `mcp`：Node MCP 服务

Hugging Face 版把它们塞进一个 Docker Space：

- 外部只暴露一个地址：`https://用户名-Spacename.hf.space`
- `/health` 和 `/api/*` 转发给 Python 后端
- `/mcp` 和 `/sse` 由 Node MCP 服务处理

## 部署步骤

### 第一步：创建 Space

1. 登录 Hugging Face。
2. 点头像旁边的 `+ New Space`。
3. 填 Space name，例如：`palm-window`。
4. SDK 选 `Docker`。
5. Hardware 保持 `CPU Basic / Free`。
6. Visibility 建议先选 `Private` 或 `Protected`。
7. 创建。

### 第二步：上传代码

把 ZIP 解压，上传所有文件。

必须确保 Space 根目录能看到这些文件：

```text
README.md
Dockerfile
start_hf.sh
server/linjian_server.py
mcp/server.js
mcp/package.json
```

### 第三步：设置 Secret

进入 Space：`Settings → Variables and secrets`。

Secret：

```text
LINJIAN_TOKEN = 自己生成的长密钥
```

Variable：

```text
LINJIAN_DEFAULT_DEVICE = my-phone
LINJIAN_KEEP = 3
```

`LINJIAN_TOKEN` 是手机 App、后端、MCP 之间的通行证，不要发给别人。

### 第四步：等待构建

上传完成后，Space 会自动 Build。

构建成功后测试：

```text
https://你的用户名-你的Space名.hf.space/health
```

如果看到：

```json
{"ok": true, "service": "linjian-unified"}
```

说明后端成功。

再测试：

```text
https://你的用户名-你的Space名.hf.space/mcp_health
```

如果看到：

```json
{"ok": true, "service": "linjian-unified-mcp"}
```

说明 MCP 成功。

## Android App 填法

```text
Server URL = https://你的用户名-你的Space名.hf.space
Token = LINJIAN_TOKEN 那串密钥
Device ID = my-phone
```

## ChatGPT / MCP 填法

新版 MCP：

```text
https://你的用户名-你的Space名.hf.space/mcp
```

旧 SSE：

```text
https://你的用户名-你的Space名.hf.space/sse
```

## 常见问题

### 1. /health 打不开

Space 可能还在 Build，或者免费硬件睡眠中。先打开 Space 页面等它醒来，再刷新 `/health`。

### 2. /health 能打开，但手机连不上

检查三项：

```text
Server URL 是否少了 https://
Token 是否和 Space Secret 完全一致
Device ID 是否是 my-phone
```

### 3. MCP 能添加，但工具调用失败

先打开 `/mcp_health`，看 `has_token` 是否为 `true`。

如果 `has_token: false`，说明没有设置 `LINJIAN_TOKEN` Secret，或者设置后没有重启 Space。

### 4. 截图/状态突然没了

免费 Space 的磁盘不是持久存储，容器重启后临时截图和状态会丢失。重新打开手机 App，让它重新上传状态即可。

### 5. 可以多人共用一个 Space 吗？

不建议。掌心窗涉及截图、手机状态和控制命令，每个人都应该用自己的 Space、自己的 Token、自己的 Device ID。

## 给小红书用户的简短版

```text
如果 Render 选 Free 也要求绑卡，可以试这个 Hugging Face Spaces 版：
1. 新建 Hugging Face Space，SDK 选 Docker，硬件选 CPU Basic Free
2. 上传 ZIP 解压后的全部文件
3. Settings 里添加 Secret：LINJIAN_TOKEN=自己的长密钥
4. 等 Build 完成，打开 /health 测试
5. 手机 App 填 Space 地址 + 同一个 Token + Device ID=my-phone
6. ChatGPT MCP 地址填 /mcp
```
