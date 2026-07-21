# MCP 工具清单（v0.3.4-public）

掌心窗 MCP 服务会把手机端能力暴露给支持 MCP 的客户端。所有工具都需要你自己的 `LINJIAN_TOKEN`，并且手机端必须保持服务启动。

## 看见与状态

- `peek_screen(wait_seconds)`：请求手机端截一张新截图，并等待上传后返回图片。
- `latest_screen()`：不触发手机截图，直接读取服务器最近一张截图。
- `linjian_status()`：检查 MCP 与后端连接状态、默认设备和最新截图信息。
- `get_life_state(device_id)`：读取生活状态：电量、充电、网络、当前 App、今日屏幕时间、解锁次数、常用 App、当前天气地区、门禁状态、周期提醒等。
- `get_phone_state(device_id)`：读取更轻量的当前包名、当前 App、无障碍状态和屏幕文本。
- `get_screen_nodes(device_id, wait_seconds)`：读取当前屏幕无障碍节点，包含文字、控件类型、可点击状态和坐标。

## 通知、天气和提醒

- `send_notification(title, message, device_id)`：给手机发一条系统通知。
- `send_weather_notification(device_id, city, title)`：按当前地区或指定城市查询天气，并发送天气提醒。
- `set_alarm(hour, minute, label, device_id)`：设置系统闹钟。

## 手机控制

- `open_app(app, package, device_id)`：打开指定 App。`app` 可填小红书、微信、QQ、抖音、ChatGPT、Gemini、Claude、微博、X，也可直接传包名。
- `phone_home(device_id)`：回桌面。
- `phone_back(device_id)`：返回。
- `phone_recents(device_id)`：打开最近任务。
- `tap(x, y, device_id)`：点击坐标。
- `swipe(x1, y1, x2, y2, duration, device_id)`：滑动。
- `tap_text(target_text, match, index, device_id)`：按屏幕文字点击。
- `input_text(text, append, device_id)`：向当前输入框输入文本。
- `run_sequence(steps, device_id)`：一次执行多步动作，适合打开 App、等待、点击、截图等连招。
- `run_preset(preset, device_id, x, y, wait_seconds)`：执行预设连招，例如 `open_xhs`、`come_home`、`bedtime_back`。

## 应用与包名

- `list_known_apps()`：列出预置 App 包名和用户手动保存的包名。
- `save_known_app(alias, package, device_id)`：保存应用昵称与包名，之后可用昵称打开。

## 小红书辅助

- `draft_xhs_comment(text, device_id)`：尝试打开评论输入框并写入草稿，不发送。
- `xhs_comment(text, mode, author_tag, device_id, wait_seconds)`：小红书评论助手。`manual` 只写草稿；`auto` 会追加署名并尝试发送，只应在你明确同意时使用。
- `send_visible_comment_after_confirmation(device_id, wait_seconds)`：在你确认当前屏幕草稿无误后，点击发送按钮。

## 应用门禁

- `lock_app(app, package, minutes, reason, message, emergency_passphrase, device_id)`：锁定一个 App。
- `unlock_app(app, package, device_id)`：解除锁定。
- `temporary_unlock_app(app, package, minutes, device_id)`：临时放行。
- `extend_lock(app, package, minutes, device_id)`：延长锁定时间。
- `deny_unlock_request(app, package, message, device_id)`：拒绝一次解锁申请。
- `get_lock_state(device_id)`：读取当前门禁状态。
- `set_emergency_passphrase(app, package, emergency_passphrase, device_id)`：设置紧急口令。
- `list_lockable_apps(device_id)`：让手机列出可作为门禁对象的已安装 App。

## 隐私边界

这些工具只适合你自己的手机和你自己的服务。不要用来管理别人的设备，不要把 Token 发给陌生客户端。截图、读屏、门禁和自动发送评论都建议默认手动确认。
