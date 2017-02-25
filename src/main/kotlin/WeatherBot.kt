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

class WeatherBot(telegramToken: String) {
    val httpClient = OkHttpClient().newBuilder()
            .readTimeout(65, TimeUnit.SECONDS)
            .build()
    val bot = TelegramBotAdapter.buildCustom(telegramToken, httpClient)

    val weatherGenerator = WeatherGenerator()

    var logger = LoggerFactory.getLogger(WeatherBot::class.java)

    fun run() {
        logger.info("Started.")
        var lastUpdateId = 0
        while (true) {
            val updates = bot.execute(GetUpdates().timeout(60).offset(lastUpdateId)).updates() ?: continue
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
                return
            }
            "help" -> {
                bot.sendText(chatId, "Daily random weather for role play chats.")
                return
            }
        }
    }

    fun TelegramBot.sendText(chatId: Long, text: String, replyMarkup: Keyboard? = ReplyKeyboardHide()) {
        this.execute(SendMessage(chatId, text).replyMarkup(replyMarkup))
    }
}
