package edu.mit.cor.f3.util;

/**
 * Created by cor on 9/17/16.
 */

import com.eclipsesource.json.*;

import java.net.*;
import java.io.*;

public class Find
{

    public static void main(String[] args) throws Exception {
        String input = "W20";
        URL search = new URL("http://m.mit.edu/apis/maps/places/?q=" + URLEncoder.encode(input, "UTF-8"));
        URLConnection c = search.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        c.getInputStream()));
        String inputLine = in.readLine();
        in.close();

        if (inputLine != null) {
            JsonArray arr = Json.parse(inputLine).asArray();
            if(arr.size() == 0) {
                System.out.println("you botched it");
                return;
            }
            JsonObject data = arr.get(0).asObject();
            System.out.println(data.getString("name", "mit"));
            double lat = data.getDouble("lat_wgs84", 0);
            double lon = data.getDouble("long_wgs84", 0);

            System.out.println("Coordinates: " + lat + ", " + lon);
        }
    }
}
