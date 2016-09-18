package edu.mit.cor.f3.util;

/**
 * Created by kathy on 9/17/2016.
 */
public class Food
{
    public String subject, body;
    public double lat, lon;
    public double time;

    public Food(String subject, String body, double[] coord, long time)  {
        this.subject = subject;
        this.body = body;
        this.lat = coord[0];
        this.lon = coord[1];
        this.time = (double) time/60000.0;
    }

}
