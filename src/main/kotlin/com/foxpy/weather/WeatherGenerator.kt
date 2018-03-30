package com.foxpy.weather

import java.time.LocalDate
import java.time.ZoneId
import java.util.Random


class WeatherGenerator {
    private val random = Random()
    private var currentWeather = ""

    private var lastUpdateDate = LocalDate.now(ZoneId.of("UTC")).minusDays(1)!!

    private val hotWeathers  = Array(20, {"rain"})    + Array(5,  {"thunderstorm"}) +
                               Array(5,  {"shower"})
    private val coldWeathers = Array(20, {"snow"})    + Array(5,  {"snowstorm"}) +
                               Array(5,  {"drizzle"})

    private val bothWeathers = Array(30, {"sunny"})   + Array(20, {"overcast"}) +
                               Array(15, {"cloudy"})  + Array(5,  {"fog"})

    private val windDirections = arrayListOf("➡️", "⬅️", "⬆️", "⬇️", "↗️", "↘️", "↙️", "↖️")

    fun getWeather(): String {
        if (lastUpdateDate != LocalDate.now(ZoneId.of("UTC"))) {
            updateDate()

            val temperature = getRandomNumber(-25, 35)
            val humidity = getRandomNumber(20, 100)
            val windSpeedMin = getRandomNumber(0, 10)
            val windSpeedMax = windSpeedMin + getRandomNumber(1, 10)
            val windDirection = windDirections[random.nextInt(windDirections.size)]
            val weathers = bothWeathers + if (temperature >= 0) hotWeathers else coldWeathers


            currentWeather = "Weather: ${weathers[random.nextInt(weathers.size)]}\n" +
                             "Temperature: $temperature" + "ºC\n" +
                             "Humidity: $humidity%\n" +
                             "Wind: $windDirection $windSpeedMin-$windSpeedMax m/s"
        }
        return currentWeather
    }

    fun changeWeather() {lastUpdateDate = LocalDate.now(ZoneId.of("UTC")).minusDays(1)}

    private fun getRandomNumber(min: Int, max: Int): Int {return random.nextInt(max + 1 - min) + min}
    private fun updateDate() {lastUpdateDate = LocalDate.now(ZoneId.of("UTC"))}
}
