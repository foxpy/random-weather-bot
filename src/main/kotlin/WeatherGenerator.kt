class WeatherGenerator {
    var random = java.util.Random()

    val weathers = arrayOf("snow", "rain", "shit")

    fun randomWeather(): String {
        return weathers[random.nextInt(weathers.size)]
    }

    fun randomTemperature() : String {
        val min = -50
        val max = 100
        return (random.nextInt(max + 1 - min) + min).toString() + "ºC"
    }
}
