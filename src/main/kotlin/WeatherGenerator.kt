import java.time.LocalDate
import java.time.ZoneId
import java.util.Random


class WeatherGenerator {
    private val random = Random()
    private var currentWeather = ""

    private var lastUpdateDate = LocalDate.now(ZoneId.of("UTC")).minusDays(1)!!

    private val hotWeathers  = Array(5,  {"rain"})    + Array(20, {"fair"}) +
                               Array(3,  {"shower"})  + Array(2,  {"thunderstorm"})
    private val coldWeathers = Array(20, {"snow"})    + Array(5,  {"snowstorm"}) +
                               Array(5,  {"drizzle"})

    private val bothWeathers = Array(25, {"sunny"})   + Array(20, {"overcast"}) +
                               Array(10, {"cloudy"})  + Array(10, {"wind"}) +
                               Array(3,  {"fog"})     + Array(2,  {"hail"})

    fun randomize(): String {
        if (lastUpdateDate != LocalDate.now(ZoneId.of("UTC"))) {
            lastUpdateDate = LocalDate.now(ZoneId.of("UTC"))

            val temperature = randomTemperature(-20, 35)
            val weathers = bothWeathers + if (temperature >= 0) hotWeathers else coldWeathers

            currentWeather = "Weather: ${weathers[random.nextInt(weathers.size)]}\n" +
                    "Temperature: $temperature" + "ºC"
        }
        return currentWeather
    }

    fun change() { lastUpdateDate = LocalDate.now(ZoneId.of("UTC")).minusDays(1) }

    private fun randomTemperature(min: Int, max: Int) : Int { return random.nextInt(max + 1 - min) + min }
}
