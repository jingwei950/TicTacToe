package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChoiceActivity extends AppCompatActivity {

    ListView listView; //Create a variable listView of type ListView that is called "listView"
    String[] listItem; //Create a String array variable of type String that is called "listItem"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        //Store the listView of id "listView" in the string.xml into variable named "listView"
        listView = (ListView) findViewById(R.id.listView);
        //Store the array of string that id 'array_technology' in the string.xml into variable named "listItem"
        listItem = getResources().getStringArray(R.array.array_choice);

        //Create a type ArrayAdapter with type of String with the variable name of adapter
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listItem);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String value = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "You have selected " + value, Toast.LENGTH_LONG).show();

                Log.i("valueTAG", value);
                Class<? extends Activity> activityToStart = null;
                switch (position){
                    case 0:
                        activityToStart = MainActivity.class;
                        break;
                    case 1:
                        activityToStart = MainActivity.class;
                        break;
                }

                Intent i = new Intent(getApplicationContext(), activityToStart);
                i.putExtra("CAPTURE_TEXT",value+"");
                startActivity(i);
            }
        });
    }
}