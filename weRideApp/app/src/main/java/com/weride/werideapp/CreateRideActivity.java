package com.weride.werideapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TimePicker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class CreateRideActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    public static LatLng startLoc;
    public static LatLng endLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ride);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng london = new LatLng(51.528308, -0.3817822);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(london));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(9));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }
    }

//    SeekBar seekBar = (SeekBar)findViewById(R.id.ageSeekBar);

    public void goToTestActivity(View view){
        EditText nameInp = (EditText)findViewById(R.id.raceNameInput);
        String name = nameInp.getText().toString();

        TimePicker timeInp = (TimePicker)findViewById(R.id.startTimePicker);
        DatePicker dateInp = (DatePicker)findViewById(R.id.datePicker);
        int min = timeInp.getMinute();
        int hour = timeInp.getHour();
        int day = dateInp.getDayOfMonth();
        int month = dateInp.getMonth();
        int year = dateInp.getYear();
        Calendar dateTime = Calendar.getInstance();
        dateTime.set(year, month, day, hour, min);

        Double startLat = startLoc.latitude;
        Double startLng = startLoc.longitude;
        Double endLat = endLoc.latitude;
        Double endLng = endLoc.longitude;

        RatingBar paceBar = (RatingBar)findViewById(R.id.paceBar);
        int pace = (int)paceBar.getRating();

        RadioButton _18_35 = (RadioButton)findViewById(R.id.radioButton18_35);
        RadioButton _36_50 = (RadioButton)findViewById(R.id.radioButton36_50);
        RadioButton _50plus = (RadioButton)findViewById(R.id.radioButton50Plus);
        int age = 0;
        if(_18_35.isChecked()){
            age = 18;
        } else if (_36_50.isChecked()){
            age = 36;
        } else if (_50plus.isChecked()){
            age = 51;
        }

        RideInfo rideInfo = new RideInfo(1, name, dateTime, startLat, startLng, endLat, endLng, pace, age);
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra("rideInfo", rideInfo);
        startActivity(intent);
    }

    public void showRouteMarkers(View view) throws IOException{
        EditText start = (EditText) findViewById(R.id.startLocEditText);
        EditText end = (EditText) findViewById(R.id.endLocEditText);

        startLoc = getAddress(start);
        endLoc = getAddress(end);
        double latMid = (startLoc.latitude+endLoc.latitude)/2;
        double lngMid = (startLoc.longitude+endLoc.longitude)/2;
        LatLng midPoint = new LatLng(latMid, lngMid);


        mMap.addMarker(new MarkerOptions().position(startLoc));
        mMap.addMarker(new MarkerOptions().position(endLoc));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(midPoint));
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+start+"&destination="+end+"&avoid=highways&mode=bicycling&key=AIzaSyBdoK6DhGq7aXZOuYgcP3igReXSDlPC9mY";

    }

    public LatLng getAddress(EditText start) throws IOException {
        String location = start.getText().toString();
        Geocoder gc=new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location,1);
        android.location.Address Address = list.get(0);
        return new LatLng(Address.getLatitude(), Address.getLongitude());

    }
    
}
