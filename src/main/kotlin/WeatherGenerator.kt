import java.util.Random


class WeatherGenerator {
    val random = Random()
    val time = Time()

    var currentWeather: String = ""

    val hotWeathers  = Array(5,  {"rain"})   + Array(20, {"fair"}) +
                       Array(3,  {"shower"}) + Array(2,  {"thunderstorm"})
    val coldWeathers = Array(20, {"snow"})   + Array(5,  {"snowstorm"}) +
                       Array(5,  {"drizzle"})

    val bothWeathers = Array(25, {"sunny"})   + Array(20, {"overcast"}) +
                       Array(10, {"cloudy"})  + Array(10, {"wind"}) +
                       Array(3,  {"fog"})     + Array(2,  {"hail"})

    fun randomize(): String {
        if (time.isNextDay()) {
            val temperature = randomTemperature(-20, 35)
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

    fun randomTemperature(min: Int, max: Int) : Int {
        return random.nextInt(max + 1 - min) + min
    }
}
