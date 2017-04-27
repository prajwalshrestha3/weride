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

    //GO TO MAP ACTIVITY
    public void goToMap(View view){
        Intent intent = new Intent(this, MapsActivity.class);   //INITIALISE A NEW INTENT FOR MAPS ACTIVITY
        startActivity(intent);  //START MAPS ACTIVITY
    }

    public void goToCreateRidePage(View view){
        Intent intent = new Intent(this, CreateRideActivity.class); //INITIALISE A NEW INTENT FOR CREATE RIDE ACTIVITY
        startActivity(intent);  //START CREATE RIDE ACTIVITY
    }
    public void goToRegisterpage (View view) {
        Intent intent = new Intent(this, Registerpage.class);   //INITIALISE A NEW INTENT FOR REGISTER PAGE ACTIVITY
        startActivity(intent);  //START REGISTER PAGE ACTIVITY
    }
    public void goToFindRidePage(View view){
        Intent intent = new Intent(this, FindRideActivity.class);   //INITIALISE A NEW INTENT FOR FIND RIDE ACTIVITY
        startActivity(intent);  //START FIND RIDE ACTIVITY
    }
}


