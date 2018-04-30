from random import randint


class WeatherGenerator:
    current_weather = str()

    def get_weather(self):
        temperature_day = randint(-25, 35)
        temperature_night = temperature_day - randint(3, 15)

        self.current_weather = f"Temperature day: {temperature_day}ºC\n" + \
            f"Temperature night: {temperature_night}ºC"

        return self.current_weather
