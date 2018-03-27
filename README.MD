# Random Weather Bot

Telegram bot which generates random weather once per day (value changes at 3 AM
MSK). May be usable in group chats for entertainment purposes.

# Commands

Command | Description
------------ | -------------
**/weather** | Send random weather to chat.
**/change** | Change weather. Available only for bot admin.

# Environment variables

Bot won't start without them. 

Variable | Description
------------ | -------------
**TELEGRAM_TOKEN** | Bot token, take it from [BotFather](https://t.me/BotFather).
**ADMIN_ID** | Your Telegram ID (used for `/change`). Taken from [IDBot](https://t.me/myidbot).
