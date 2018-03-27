package com.foxpy.weather

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.TelegramBotAdapter
import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.model.request.Keyboard
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove
import com.pengrad.telegrambot.request.GetUpdates
import com.pengrad.telegrambot.request.SendMessage
import okhttp3.OkHttpClient
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit


class WeatherBot(telegramToken: String, adminId: String) {
    val httpClient: OkHttpClient = OkHttpClient().newBuilder()
            .readTimeout(65, TimeUnit.SECONDS).build()

    val bot = TelegramBotAdapter.buildCustom(telegramToken, httpClient)!!
    val adminId = adminId.toInt()

    val weatherGenerator = WeatherGenerator()

    val logger = LoggerFactory.getLogger(WeatherBot::class.java)!!

    fun run() {
        logger.info("[Started]")
        var lastUpdateId = 0

        while (true) {
            val updates = try {bot.execute(GetUpdates().timeout(60).offset(lastUpdateId)).updates() ?: continue}
            catch (e: Exception) {
                Thread.sleep(500)
                logger.error("Network unreachable")
                continue
            }

            for (update in updates) {
                lastUpdateId = update.updateId() + 1
                val message = update.message()
                message?.text()?.let {processMessage(message)}
            }
        }
    }

    fun processMessage(msg: Message) {
        val chatId = msg.chat().id()
        val text = msg.text()

        logger.info("[$chatId] [Received] [@${msg.from().username()}: $text]")

        when {
            text.startsWith("/weather") -> {
                bot.sendText(chatId, weatherGenerator.getWeather())
            }
            text.startsWith("/change") -> {
                if (msg.from().id() == adminId) {
                    weatherGenerator.changeWeather()
                    bot.sendText(chatId, "Success!")
                }
                else bot.sendText(chatId, "Nice try!")
            }
            text.startsWith("/help") -> bot.sendText(chatId, "Just type '/weather'.")
            text.startsWith("/start") -> bot.sendText(chatId, "Daily random weather for role play chats.")
        }
    }

    private fun TelegramBot.sendText(chatId: Long, text: String, replyMarkup: Keyboard? = ReplyKeyboardRemove()) {
        this.execute(SendMessage(chatId, text).replyMarkup(replyMarkup))
    }
}
