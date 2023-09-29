package com.thuy.weatherapp.models;

import com.thuy.weatherapp.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class City {
    public String mName;
    public String mDescription;
    public String mTemperature;
    public int mWeatherIcon;
    public String mCountry;
    public int mIdCity;
    public double mLatitude;
    public double mLongitude;
    public int mWeatherResIconWhite;
    public int mWeatherResIconGrey;


    public City(String name, String description, String temperature, int weatherIcon) {
        this.mName = name;
        this.mDescription = description;
        this.mTemperature = temperature;
        this.mWeatherIcon = weatherIcon;
    }

    public City(String stringJson) throws JSONException {
        JSONObject json = new JSONObject(stringJson);

        JSONObject details = json.getJSONArray("weather").getJSONObject(0);
        JSONObject main = json.getJSONObject("main");
        JSONObject coord = json.getJSONObject("coord");

        mIdCity = json.getInt("id");
        mName = json.getString("name");
        mCountry = json.getJSONObject("sys").getString("country");
        mTemperature = String.format("%.0f", main.getDouble("temp")) + " ℃";
        mDescription = Util.capitalize(details.getString("description"));
        mWeatherResIconWhite = Util.setWeatherIcon(details.getInt("id"), json.getJSONObject("sys").getLong("sunrise") * 1000, json.getJSONObject("sys").getLong("sunset") * 1000);
        mWeatherResIconGrey = Util.setWeatherIcon(details.getInt("id"));
        mLatitude = coord.getDouble("lat");
        mLongitude = coord.getDouble("lon");
    }


}
