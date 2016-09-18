package edu.mit.cor.f3.util;

/**
 * Created by kathy on 9/17/2016.
 */
public class Food implements Comparable<Food>
{
    public String subject, body;
    public double lat, lon;
    public double time, distance;

    public Food(String subject, String body, double[] coord, long time)  {
        this.subject = subject;
        this.body = body;
        this.lat = coord[0];
        this.lon = coord[1];
        this.time = (double)time/3600000.0;
        this.distance = 0;
    }

    public double getScore() {
        return Math.pow(0.5, 3 * distance) * Math.pow(0.5, time * 1.2);
    }

    public int compareTo(Food f){
        return (getScore() > f.getScore()) ? -1: 1;
    }
}
