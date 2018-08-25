class WeatherType:
    def __init__(self, weathers, temperature_range, temperature_night_offset,
            wind_range, humidity_range):
        self.__weathers = weathers
        self.__min_temp_day, self.__max_temp_day = \
                tuple(temperature_range)
        self.__min_temp_night, self.__max_temp_night = \
                tuple(map(lambda x: x- temperature_night_offset,
                    temperature_range))
        self.__min_wind_speed, self.__max_wind_speed = \
                tuple(wind_range)
        self.__min_humidity, self.__max_humidity = \
                tuple(humidity_range)
