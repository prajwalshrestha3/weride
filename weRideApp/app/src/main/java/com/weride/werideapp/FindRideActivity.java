package com.weride.werideapp;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.R.color.black;

public class FindRideActivity extends ListActivity {

    //THE FOLLOWING CODE HAS BEEN ADAPTED FROM CODE GIVEN IN AN ANSWER ON STACKOVERFLOW:
    // http://stackoverflow.com/questions/4540754/dynamically-add-elements-to-a-listview-android
    //THE FIRST ANSWER BY SHARDUL, I HAVE ADDED AND REMOVED CODE TO SUIT MY PURPOSES

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> searchResults = new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_find_ride);

        //adapter instantiation with inner class declaration to change colour of text in listview taken from shardul's answer on
        //http://stackoverflow.com/questions/4602902/how-to-set-the-text-color-of-textview-in-code
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchResults){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                textView.setTextColor(Color.BLACK);

                return view;
            }
        };

        setListAdapter(adapter);

    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) {
        Calendar now = Calendar.getInstance();
        now.set(2017, 4, 19, 6, 30);
        RideInfo r1 = new RideInfo(1, "r1", now, 53.2, 40, 52, 23, "12 km", 3, 18);
        searchResults.add(r1.infoToString());
        searchResults.add(r1.infoToString());
        searchResults.add(r1.infoToString());
        adapter.notifyDataSetChanged();
    }
}
