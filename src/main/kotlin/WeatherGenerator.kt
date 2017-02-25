class WeatherGenerator {
    private var random = java.util.Random()

    val hotWeathers = arrayOf("rain", "fair", "shower")
    val coldWeathers = arrayOf("snow", "hail", "snowstorm")
    val bothWeathers = arrayOf("fog", "wind")

    fun randomTemperature() : Int {
        val min = -50
        val max = 100
        return random.nextInt(max + 1 - min) + min
    }

    fun randomize(): String {
        val currentWeather: String

        val temperature = randomTemperature()
        if (temperature >= 0) {
            val weathers = hotWeathers + bothWeathers
            currentWeather = weathers[random.nextInt(weathers.size)]
        }
        else {
            val weathers = coldWeathers + bothWeathers
            currentWeather = weathers[random.nextInt(weathers.size)]
        }
        return "Weather: $currentWeather\n" +
                "Temperature: ${temperature}ºC"
    }
}
