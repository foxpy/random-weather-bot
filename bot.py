from telegram.ext import Updater, CommandHandler
import logging
from weathergenerator import WeatherGenerator


logging.basicConfig(format='%(asctime)s - %(levelname)s - %(message)s',
                    level=logging.INFO)
logger = logging.getLogger(__name__)

weather_generator = WeatherGenerator()


class WeatherBot:
    def __init__(self, token, admin_id):
        self.token = token
        self.admin_id = admin_id


    def log(self, command_type, chat_id, message_text):
        logger.info("[{}] - [{}] - Received: {}".format(command_type, chat_id,
            message_text))


    def start(self, bot, update):
        update.message.reply_text("Daily random weather for role play chats.")


    # I don't want to override Python's built-in help object
    def help_cmd(self, bot, update):
        update.message.reply_text("Just type '/weather'.")


    def weather(self, bot, update):
        update.message.reply_text(weather_generator.get_weather())
        self.log("WEATHER", update.effective_chat.id, update.message.text)


    def change(self, bot, update):
        # FIXME: everyone has power to change weather
        weather_generator.change_weather()
        self.log("CHANGE", update.effective_chat.id, update.message.text)


    def error(self, bot, update, error):
        logger.warning("Update \"%s\" caused error \"%s\"", update, error)


    def run(self):
        self.updater = Updater(self.token)

        self.dp = self.updater.dispatcher

        self.dp.add_handler(CommandHandler("start", self.start))
        self.dp.add_handler(CommandHandler("help", self.help_cmd))
        self.dp.add_handler(CommandHandler("weather", self.weather))
        self.dp.add_handler(CommandHandler("change", self.change))

        self.dp.add_error_handler(self.error)

        self.updater.start_polling()
        self.updater.idle()
