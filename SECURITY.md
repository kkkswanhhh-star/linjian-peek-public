# Security Notes

Palm Window MCP exposes sensitive abilities: screenshots, accessibility actions, notifications, alarms, and usage state. Use it only on devices you own or devices where the user has explicitly opted in.

Recommended rules:

1. Generate a long random `LINJIAN_TOKEN` and never commit it.
2. Keep both the backend server and MCP endpoint private where possible.
3. Do not connect untrusted AI clients to your MCP endpoint.
4. Avoid screenshots or automation on payment, wallet, chat, password, or verification-code pages.
5. Require user confirmation before any high-impact action such as payment, ordering, deletion, sending messages, or changing account settings.
6. Turn off the Android service or revoke accessibility permission when not using it.



## Hugging Face Spaces 版提醒

请把 `LINJIAN_TOKEN` 放在 Space 的 Secret 里，不要写进 `README.md`、代码、公开截图或评论区。不要多人共用同一个 Space 和 Token。
