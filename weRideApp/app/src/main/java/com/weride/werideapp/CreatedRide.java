package com.weride.werideapp;

import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Date;

public class CreatedRide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_ride);
        RideInfo rideInfo = (RideInfo)getIntent().getSerializableExtra("rideInfo"); //GET RIDEINFO OBJECT FROM INTENT
        TextView tv = (TextView)findViewById(R.id.rideNameValue);   //NEW TEXTVIEW OBJECT TO HANDLE TEXT OF RIDENAMEVALUE TEXTVIEW IN CREATED RIDE PAGE
        tv.setText(rideInfo.rideName);  //SET TEXT OF RIDE NAME TO RIDENAME STORED IN RIDEINFO OBJECT
        tv = (TextView)findViewById(R.id.rideDateTimeValue);     //NEW TEXTVIEW OBJECT TO HANDLE TEXT OF RIDEDATETIMEVALUE TEXTVIEW IN CREATED RIDE PAGE
        Calendar calendar = Calendar.getInstance(); //CREATE NEW CALENDAR INSTANCE
        calendar.setTime(rideInfo.dateTime.getTime());  //SET TIME OF CALENDAR INSTANCE TO THE DATE AND TIME IN RIDEINFO OBJECT
        //STRING TO STORE THE DATE AND TIME OF THE RIDE SO THAT IT CAN BE DISPLAYED IN A FRIENDLY WAY TO THE USER
        String dateTime = (calendar.get(Calendar.HOUR_OF_DAY)+1) + ":"  + calendar.get(Calendar.MINUTE) + "  " + calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
        tv.setText(dateTime);    //SET TEXT OF RIDE DATE AND TIME TO  STORED IN RIDEINFO OBJECT
        tv = (TextView)findViewById(R.id.rideStartLocValue);     //NEW TEXTVIEW OBJECT TO HANDLE TEXT OF RIDESTARTLOCVALUE TEXTVIEW IN CREATED RIDE PAGE
        tv.setText(getIntent().getStringExtra("startAddress")); //GET START ADDRESS STRING FROM INTENT
        tv = (TextView)findViewById(R.id.rideEndLocValue);   //NEW TEXTVIEW OBJECT TO HANDLE TEXT OF RIDEENDLOCVALUE TEXTVIEW IN CREATED RIDE PAGE
        tv.setText(getIntent().getStringExtra("endAddress"));   //GET END ADDRESS STRING FROM INTENT
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ridePaceBarValue);   //NEW RATINGBAR OBJECT TO HANDLE TEXT OF RIDEPACEVALUE RATINGBAR IN CREATED RIDE PAGE
        ratingBar.setRating(rideInfo.pace); //SET NUMBER OF STARS RATING FOR PACE RATING BAR
        tv = (TextView)findViewById(R.id.rideAgeValue);  //NEW TEXTVIEW OBJECT TO HANDLE TEXT OF RIDEAGEVALUE TEXTVIEW IN CREATED RIDE PAGE
        if(rideInfo.age == 18){
            tv.setText("18-35");     //SET TEXT OF RIDE AGE TO 18-35 IF AGE == 18 STORED IN RIDEINFO OBJECT
        } else if(rideInfo.age == 36){
            tv.setText("36-50");    //SET TEXT OF RIDE AGE TO 36-50 IF AGE == 36 STORED IN RIDEINFO OBJECT
        } else {
            tv.setText("50+");  //SET TEXT OF RIDE AGE TO 50+ IF AGE == 50 STORED IN RIDEINFO OBJECT
        }


    }

    public void goBackToHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
