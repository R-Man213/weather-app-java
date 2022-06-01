package com.example.weatherapp;

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
public class LocationService {

    //Sign up at https://openweathermap.org/ to get a valid API key
    //Then Copy and paste it between the quotes below
    //You will also need to copy and paste this key in WeatherService.java
    private static final String API_KEY = "";
    private String city;
    private String state;
    private String country;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Coordinates getLocation() throws IOException, ParseException {
        Coordinates coordinates = new Coordinates();
        String jsonString = getEntireLocationJson();

        //Get Lat and Lon
        for(int i = 5; i < jsonString.length();i++)
        {
            if (jsonString.substring(i-5,i).equals("lat\":"))
            {
                StringBuilder latString = new StringBuilder();
                int j = i;
                while(jsonString.charAt(j) != ',')
                {
                    latString.append(jsonString.charAt(j));
                    j++;
                }
                coordinates.setLatitude(Double.parseDouble(latString.toString()));
            }
            else if(jsonString.substring(i-5,i).equals("lon\":"))
            {
                StringBuilder lonString = new StringBuilder();
                int j = i;
                while(jsonString.charAt(j) != ',')
                {
                    lonString.append(jsonString.charAt(j));
                    j++;
                }
                coordinates.setLongitude(Double.parseDouble(lonString.toString()));
            }

        }
        return coordinates;
    }

    private String getEntireLocationJson() throws IOException, ParseException {
        BufferedReader bufferedJson = getBufferedJson();

        String line;
        StringBuilder jsonString = new StringBuilder();

        while((line = bufferedJson.readLine()) != null)
        {
            jsonString.append(line);
            jsonString.append(System.lineSeparator());
        }

        return jsonString.toString();
    }

    private BufferedReader getBufferedJson()
    {
        try {
            URL url = new URL(constructApiCall(city,state,country));
            URLConnection connection = url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            return in;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader notWorking = new BufferedReader(new StringReader("Cannot Read Buffer"));
        return notWorking;
    }

    private String constructApiCall(String city, String state, String country) {
        String url;

        if(city == null || city.length() == 0)
        {
            city = "Paris";
        }

        if(country == null || city.length() == 0)
        {
            country = "France";
        }

        if (state == null || state.length() == 0) {
            url = "http://api.openweathermap.org/geo/1.0/direct?q=" +
                    city + ",," + country + "&limit=1&appid=" + API_KEY;
        } else {
            url = "http://api.openweathermap.org/geo/1.0/direct?q=" +
                    city + "," + state + "," + country + "&limit=1&appid=" + API_KEY;
        }

        return url;
    }
}
