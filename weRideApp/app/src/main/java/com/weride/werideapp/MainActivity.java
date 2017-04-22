package com.weride.werideapp;


import android.content.pm.ActivityInfo;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
    }

    public void goToMap(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void goToCreateRidePage(View view){
        Intent intent = new Intent(this, CreateRideActivity.class);
        startActivity(intent);
    }

    public void goToFindRidePage(View view){
        Intent intent = new Intent(this, FindRideActivity.class);
        startActivity(intent);
    }
}


