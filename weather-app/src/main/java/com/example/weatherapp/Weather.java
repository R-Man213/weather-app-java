package com.example.weatherapp;

public class Weather {
    private String weatherDescription;
    private double temp;
    private double tempMin;
    private double tempMax;
    private double humidity;
    private Coordinates coordinates;

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public Coordinates getLocation() {
        return coordinates;
    }

    public void setLocation(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Weather(){};

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "temp=" + temp +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", humidity=" + humidity +
                ", location=" + coordinates +
                '}';
    }
}
