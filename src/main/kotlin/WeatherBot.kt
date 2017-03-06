import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.TelegramBotAdapter
import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.model.request.Keyboard
import com.pengrad.telegrambot.model.request.ReplyKeyboardHide
import com.pengrad.telegrambot.request.GetUpdates
import com.pengrad.telegrambot.request.SendMessage
import okhttp3.OkHttpClient
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit


@Suppress("VARIABLE_WITH_REDUNDANT_INITIALIZER")
class WeatherBot(telegramToken: String, adminId : String) {
    val httpClient: OkHttpClient = OkHttpClient().newBuilder()
            .readTimeout(65, TimeUnit.SECONDS)
            .build()

    val bot: TelegramBot = TelegramBotAdapter.buildCustom(telegramToken, httpClient)
    val adminId = adminId.toLong()

    val weatherGenerator = WeatherGenerator()

    var logger = LoggerFactory.getLogger(WeatherBot::class.java)

    fun run() {
        logger.info("Started.")
        var lastUpdateId = 0
        var updates = bot.execute(GetUpdates().timeout(60).offset(lastUpdateId)).updates()

        while (true) {
            try {
                updates = bot.execute(GetUpdates().timeout(60).offset(lastUpdateId)).updates() ?: continue
            }
            catch(e: Exception) {
                continue
            }

            for (update in updates) {
                lastUpdateId = update.updateId() + 1
                val message = update.message()
                message?.text()?.let { processMessage(message) }
            }
        }
    }

    fun processMessage(msg: Message) {
        val chatId = msg.chat().id()
        val from = msg.from()
        logger.info("[${from.id()}] [Received] @${from.username()}: ${msg.text()}")

        var text = msg.text()
        if (text.startsWith("/")) text = text.slice(1..text.lastIndex)

        when (text) {
            "weather" -> {
                val reply = weatherGenerator.randomize()
                bot.sendText(chatId, reply)
            }
            "debug" -> {
                if (chatId == adminId) {
                    weatherGenerator.time.day--
                    logger.info("Day set to ${weatherGenerator.time.day}")
                    bot.sendText(chatId, "Success!")
                }
                else {
                    bot.sendText(chatId, "Nice try!")
                }
            }
            "help" -> {
                bot.sendText(chatId, "Daily random weather for role play chats.")
            }
        }
    }

    fun TelegramBot.sendText(chatId: Long, text: String, replyMarkup: Keyboard? = ReplyKeyboardHide()) {
        this.execute(SendMessage(chatId, text).replyMarkup(replyMarkup))
    }
}
