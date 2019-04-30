package Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForecastParser {
    private JSONArray list;
    private List listOfReports; // Each report has: [0] = temp, [1] = main, [2] = description, [3] = wind, [4] = time

    public ForecastParser(String s) throws IOException {
        listOfReports = new ArrayList();
        parseAll(s);
        parseReports();
    }

    private void parseAll(String s) throws IOException {
        String id = Integer.toString(new CitiesAndIDs().getID(s));
        URL u = new URL("http://api.openweathermap.org/data/2.5/forecast?id=" + id +"&APPID=1ff8a110e9bb3e48ed134abf1b176ae6&units=metric&cnt=8");
        BufferedReader b = new BufferedReader(new InputStreamReader(u.openStream()));
        JSONObject j = new JSONObject(b.readLine());
        list = j.getJSONArray("list");
    }

    private void parseReports() {
        for (Object j : list) {
            List aList = new ArrayList();
            JSONObject json = (JSONObject) j;

            aList.add(json.getJSONObject("main").getDouble("temp"));
            aList.add(json.getJSONArray("weather").getJSONObject(0).getString("main"));
            aList.add(json.getJSONArray("weather").getJSONObject(0).getString("description"));
            aList.add(Double.toString(json.getJSONObject("wind").getDouble("speed")) + " m/s");

            Long time = json.getLong("dt");
            Date date = new java.util.Date(time*1000L);
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM d,YYYY (EEE) @ h:mm a");
            String fd = sdf.format(date);
            aList.add(fd);

            listOfReports.add(aList);
        }
    }

    public List getListOfReports() {
        return listOfReports;
    }
}
