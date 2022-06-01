package com.example.weatherapp;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Service
public class WeatherService {

    //Sign up at https://openweathermap.org/ to get a valid API key
    //Then Copy and paste it between the quotes below
    //You will also need to copy and paste this key in LocationService.java
    private static final String API_KEY = "";

    public void setLocation(Coordinates coordinates) {
        this.LOCATION = coordinates;
    }

    public Coordinates getLocation() {
        return LOCATION;
    }

    Coordinates paris = new Coordinates(48.8566, 2.3522);
    private Coordinates LOCATION;

    private JSONObject getEntireWeatherJSon() throws IOException, ParseException {
        BufferedReader bufferedJson = getBufferedJson();

        String line;
        StringBuilder jsonString = new StringBuilder();

        while((line = bufferedJson.readLine()) != null)
        {
            jsonString.append(line);
            jsonString.append(System.lineSeparator());
        }

        JSONParser parser = new JSONParser();
        JSONObject weatherJSON = (JSONObject) parser.parse(jsonString.toString());

        return weatherJSON;
    }

    public Weather getWeather() throws IOException, ParseException {

        JSONObject weatherJson = getEntireWeatherJSon();
        JSONObject coreTempParameters = (JSONObject) weatherJson.get("main");
//
        String jsonString = getEntireWeatherJSon().toJSONString();
        for(int i = 14; i < jsonString.length();i++)
        {
            if (jsonString.substring(i-14,i).equals("description\":\""))
            {
                StringBuilder descriptionString = new StringBuilder();
                int j = i;
                while(jsonString.charAt(j) != '\"')
                {
                    descriptionString.append(jsonString.charAt(j));
                    j++;
                }
                jsonString = descriptionString.toString();
                break;
            }
        }

        Weather weather = new Weather();
        weather.setWeatherDescription(jsonString);
        weather.setHumidity(Double.parseDouble(coreTempParameters.get("humidity").toString()));
        weather.setTemp(Double.parseDouble(coreTempParameters.get("temp").toString()));
        weather.setTempMax(Double.parseDouble((coreTempParameters.get("temp_max").toString())));
        weather.setTempMin(Double.parseDouble(coreTempParameters.get("temp_min").toString()));

        return weather;
    }

    private BufferedReader getBufferedJson()
    {
        try {
            URL url = new URL(constructApiCall(LOCATION));
            URLConnection connection = url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            return in;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader notWorking = new BufferedReader(new StringReader("Doesnt work"));
        return notWorking;
    }

    private String constructApiCall(Coordinates location)
    {
                if(location == null)
        {
            location.setLatitude(paris.getLatitude());
            location.setLongitude(paris.getLongitude());
        }

        String url = "https://api.openweathermap.org/data/2.5/weather?lat="+ location.getLatitude() +
                     "&lon=" + location.getLongitude() +
                     "&appid=" + API_KEY + "&units=imperial";

        return url;
    }
}
