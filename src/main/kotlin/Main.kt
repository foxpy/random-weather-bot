fun main(args: Array<String>) {
    val telegramToken = getEnv("TELEGRAM_TOKEN")
    val bot = WeatherBot(telegramToken)

    bot.run()
}

fun getEnv(name: String): String = System.getenv(name)
        ?: throw IllegalArgumentException("You must set $name env before start")
