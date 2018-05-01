FROM python:latest
ADD . /app
WORKDIR /app
RUN pip install -r requirements.txt
CMD [ "python", "random-weather-bot" ]
