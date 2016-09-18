package edu.mit.cor.f3;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import edu.mit.cor.f3.util.Food;
import edu.mit.cor.f3.util.Mail;

public class MainActivity extends ListActivity {
    TextView content;
    Food[] foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = (TextView)findViewById(R.id.output);

    String[] values = new String[] { "free food outside building 50 eom\n\t15 min ago, 0.4mi", "Adapter implementation", "Simple List View With ListActivity",
                "ListActivity Android", "Android Example", "ListActivity Source Code", "ListView ListActivity Array Adapter", "Android Example ListActivity" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);


        // Assign adapter to List
        setListAdapter(adapter);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                foods = Mail.read();
                String[] text = new String[foods.length];
                for(int q=0; q<foods.length; q++) {
                    text[q] = foods[q].subject + "\n\t" + (int)foods[q].time + ", 0.0mi";
                }
            }
        });

        t.start();
    }

    private void updateFood() {
        Food[] foods = Mail.read();
        String[] text = new String[foods.length];
        for(int q=0; q<foods.length; q++) {
            text[q] = foods[q].subject + "\n\t" + (int)foods[q].time + ", 0.0mi";
        }
        String[] values = text;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);


        // Assign adapter to List
        setListAdapter(adapter);
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
}
