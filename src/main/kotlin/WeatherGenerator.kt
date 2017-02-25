import java.util.*


class WeatherGenerator {
    var random = Random()

    var unixTime = Date().time / 1000
    var day = unixTime / (60*60*24) - 1
    var currentWeather: String = ""

    val hotWeathers = arrayOf("rain", "fair", "shower", "thunderstorm")
    val coldWeathers = arrayOf("snow", "snowstorm", "drizzle")
    val bothWeathers = arrayOf("fog", "wind", "hail", "sunny", "overcast", "cloudy")

    fun randomTemperature() : Int {
        val min = -20
        val max = 35
        return random.nextInt(max + 1 - min) + min
    }

    fun randomize(): String {
        unixTime = Date().time / 1000
        if ((unixTime / (60*60*24)) > day) {
            day = unixTime / (60*60*24)

            val temperature = randomTemperature()
            var weathers = bothWeathers

            if (temperature >= 0) weathers += hotWeathers
            else weathers += coldWeathers

            currentWeather = "Weather: ${weathers[random.nextInt(weathers.size)]}\n" +
                    "Temperature: ${temperature}ºC"
            return currentWeather
        }
        else {
            return currentWeather
        }
    }
}
