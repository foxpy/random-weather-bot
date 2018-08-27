from random import choice, randint

degrees = chr(0xBA) + "C"
wind_directions = [chr(0x2B06) + chr(0xFE0F),   # N
                   chr(0x27A1) + chr(0xFE0F),   # E
                   chr(0x2B05) + chr(0xFE0F),   # W
                   chr(0x2B07) + chr(0xFE0F),   # S
                   chr(0x2197) + chr(0xFE0F),   # NE
                   chr(0x2196) + chr(0xFE0F),   # NW
                   chr(0x2198) + chr(0xFE0F),   # SE
                   chr(0x2199) + chr(0xFE0F)]   # SW


class WeatherType:
    def __init__(self, weathers, temperature_day_range,
            temperature_night_offset_range, wind_range, humidity_range):
        self.__weathers = weathers
        self.__min_temp_day, self.__max_temp_day = \
                tuple(temperature_day_range)
        self.__min_temp_night_offset, self.__max_temp_night_offset = \
                tuple(temperature_night_offset_range)
        self.__min_wind_speed, self.__max_wind_speed, \
        self.__min_wind_speed_offset, self.__max_wind_speed_offset = \
                tuple(wind_range)
        self.__min_humidity, self.__max_humidity = \
                tuple(humidity_range)

    def get_weather(self):
        weather = choice(self.__weathers)
        temperature_day = randint(self.__min_temp_day, self.__max_temp_day)
        temperature_night = temperature_day - \
                randint(self.__min_temp_night_offset,
                        self.__max_temp_night_offset)
        humidity = randint(self.__min_humidity, self.__max_humidity)
        wind_speed_min = randint(self.__min_wind_speed, self.__max_wind_speed)
        wind_speed_max = wind_speed_min + \
                randint(self.__min_wind_speed_offset,
                        self.__max_wind_speed_offset)
        wind_direction = choice(wind_directions)

        return f"Weather: {weather}\n" + \
            f"Temperature day: {temperature_day}{degrees}\n" + \
            f"Temperature night: {temperature_night}{degrees}\n" + \
            f"Humidity: {humidity}%\n" + \
            f"Wind speed: {wind_direction} {wind_speed_min}-{wind_speed_max}" +\
            " m/s\n"


class WeatherGenerator():
    weather_types = \
            [WeatherType(["sunny", "cloudy", "rain"],
                    (3, 40), (2, 8), (2, 10, 3, 10), (10, 90)),
            WeatherType(["sunny", "cloudy", "snow"],
                    (-25, 0), (3, 10), (2, 10, 3, 10), (10, 90)),
            WeatherType(["fog", "overcast", "drizzle"],
                    (2, 22), (2, 4), (0, 3, 1, 2), (80, 95)),
            WeatherType(["thunderstorm"],
                    (3, 30), (2, 6), (4, 14, 5, 12), (60, 95))]

    def get_weather(self):
        return choice(self.weather_types).get_weather()
