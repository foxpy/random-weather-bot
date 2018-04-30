from telegram.ext import Updater, CommandHandler
import logging
from weathergenerator import WeatherGenerator


logging.basicConfig(format='%(asctime)s - %(levelname)s - %(message)s',
                    level=logging.INFO)
logger = logging.getLogger(__name__)

weather_generator = WeatherGenerator()


def log(command_type, chat_id, message_text):
    logger.info("[{}] - [{}] - Received: {}".format(command_type, chat_id,
        message_text))


def start(bot, update):
    update.message.reply_text("Daily random weather for role play chats.")
    log("START", update.effective_chat.id, update.message.text)


# I don't want to override Python's built-in help object
def help_cmd(bot, update):
    update.message.reply_text("Just type '/weather'.")
    log("HELP", update.effective_chat.id, update.message.text)


def weather(bot, update):
    update.message.reply_text(weather_generator.get_weather())
    log("WEATHER", update.effective_chat.id, update.message.text)


def error(bot, update, error):
    logger.warning("Update \"%s\" caused error \"%s\"", update, error)


def run(token, admin_id):
    updater = Updater(token)

    dp = updater.dispatcher

    dp.add_handler(CommandHandler("start", start))
    dp.add_handler(CommandHandler("help", help_cmd))
    dp.add_handler(CommandHandler("weather", weather))

    dp.add_error_handler(error)

    updater.start_polling()
    updater.idle()
