package Model;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentParser {
    private JSONObject all;
    private String temp;
    private String weather;
    private Image weatherIcon;
    private String wind;
    private String timeOfInfo;

    public CurrentParser(String s) throws IOException {
        parseAll(s);
        parseTemp();
        parseWeather();
        parseWind();
        setTimeOfInfo();
    }

    private void parseAll(String s) throws IOException {
        String id = Integer.toString(new CitiesAndIDs().getID(s));
        URL u = new URL("http://api.openweathermap.org/data/2.5/weather?id=" + id +"&APPID=1ff8a110e9bb3e48ed134abf1b176ae6&units=metric");
        BufferedReader b = new BufferedReader(new InputStreamReader(u.openStream()));
        all = new JSONObject(b.readLine());
    }

    private void parseTemp() {
        JSONObject main = all.getJSONObject("main");
        temp = Double.toString(main.getDouble("temp")) + "°C";
    }

    private void parseWeather() throws IOException {
        JSONArray weather = all.getJSONArray("weather");
        this.weather = weather.getJSONObject(0).getString("main") +
               " : " + weather.getJSONObject(0).getString("description");

        String s = weather.getJSONObject(0).getString("icon");
        URL u = new URL("http://openweathermap.org/img/w/" + s + ".png");
        weatherIcon = ImageIO.read(u);
    }

    private void parseWind() {
        JSONObject wind = all.getJSONObject("wind");
        this.wind = Double.toString(wind.getDouble("speed")) + " m/s";
    }

    private void setTimeOfInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        timeOfInfo = sdf.format(date);
    }

    public String getTemp() {
        return temp;
    }
    public String getWeather() {
        return weather;
    }
    public Image getWeatherIcon() {
        return weatherIcon;
    }
    public String getWind() {
        return wind;
    }
    public String getTimeOfInfo() {
        return timeOfInfo;
    }
}
