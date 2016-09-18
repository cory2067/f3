package edu.mit.cor.f3;

import android.Manifest;
import android.app.ListActivity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Arrays;

import edu.mit.cor.f3.util.Food;
import edu.mit.cor.f3.util.Mail;

public class MainActivity extends ListActivity {
    TextView content;
    Food[] foods;
    ArrayAdapter<String> adapter;
    String[] values;
    Location initial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = (TextView)findViewById(R.id.output);

        values = new String[] {"\n","\n","\n","\n","\n","\n","\n","\n","\n","\n"};

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);

        // Assign adapter to List
        setListAdapter(adapter);

        loadFoodList();

        requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1337);


        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                updateLoc(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {}
            @Override
            public void onProviderDisabled(String provider) {}
        };

        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
            initial = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        catch(SecurityException e) {
            System.out.println("god dammdvxfsghtjit");
            e.printStackTrace();
        }


    }

    public void refresh(View v) {
        loadFoodList();
    }

    public void loadFoodList() {
        ((TextView) findViewById(R.id.output)).setText("F3 -- Loading");
        Thread t = new Thread() {
            @Override
            public void run() {
                foods = Mail.read();
                updateLoc(initial);
                Arrays.sort(foods);
                //String[] text = new String[foods.length];
                for(int q=0; q < Math.min(foods.length, 9); q++) {
                    values[q] = foods[q].subject + "\n\t" + (Double.toString(foods[q].time)).substring(0,4) + " hours ago, " + (Double.toString(foods[q].distance)).substring(0, 4) + "mi";
                }


                //values = text;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        ((TextView) findViewById(R.id.output)).setText("F3");
                    }
                });
            }};

        t.start();
    }

    private void updateLoc(Location loc) {
        System.out.println(loc.getLatitude());
        System.out.println(loc.getLongitude());
        for(Food food : foods) {
            food.distance = distance(loc.getLatitude(), loc.getLongitude(), food.lat, food.lon);
        }
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        //        android.R.layout.simple_list_item_1, values);


        // Assign adapter to List
        //setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);

        /*/// ListView Clicked item index
        int itemPosition     = position;

        // ListView Clicked item value
        String  itemValue    = (String) l.getItemAtPosition(position);

        content.setText("Click : \n  Position :"+itemPosition+"  \n  ListItem : " +itemValue);
*/
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts decimal degrees to radians						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
