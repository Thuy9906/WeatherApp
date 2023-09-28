package com.thuy.weatherapp.models;

public class City {
    public String mName;
    public String mDescription;
    public String mTemperature;
    public int mWeatherIcon;

    public City(String name, String description, String temperature, int weatherIcon) {
        this.mName = name;
        this.mDescription = description;
        this.mTemperature = temperature;
        this.mWeatherIcon = weatherIcon;
    }
}
