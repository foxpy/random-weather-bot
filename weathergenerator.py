from random import choice, randint

class WeatherType:
    def __init__(self, weathers, temperature_day_range,
            temperature_night_range, wind_range, humidity_range):
        self.__weathers = weathers
        self.__min_temp_day, self.__max_temp_day = \
                tuple(temperature_day_range)
        self.__min_temp_night, self.__max_temp_night = \
                tuple(temperature_night_range)
        self.__min_wind_speed, self.__max_wind_speed = \
                tuple(wind_range)
        self.__min_humidity, self.__max_humidity = \
                tuple(humidity_range)

    def get_weather(self):
        weather = choice(self.__weathers)
        temperature_day = randint(self.__min_temp_day, self.__max_temp_day)
        temperature_night = randint(self.__min_temp_night,
                self.__max_temp_night)
        humidity = randint(self.__min_humidity, self.__max_humidity)
        wind_speed = randint(self.__min_wind_speed, self.__max_wind_speed)

        return f"Weather: {weather}\n" + \
            f"Temperature day: {temperature_day}\n" + \
            f"Temperature night: {temperature_night}\n" + \
            f"Humidity: {humidity}\n" + \
            f"Wind speed: {wind_speed}\n"

weather_types = \
        [WeatherType(["sunny", "cloudy", "rain"],
                (3, 40), (-2, 37), (2, 20), (10, 90)),
        WeatherType(["sunny", "cloudy", "snow"],
                (-25, 0), (-32, -2), (2, 20), (10, 90))]
