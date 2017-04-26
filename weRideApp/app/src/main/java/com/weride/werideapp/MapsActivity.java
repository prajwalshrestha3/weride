package com.weride.werideapp;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringDef;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap; //new google map object created
    private final static int MY_PERMISSION_FINE_LOCATION = 101; //setting value 101 to an int variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //function provided by android studios when the map activity is started
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in london and move the camera.

        LatLng london = new LatLng(51.528308, -0.3817822);  //setting the lat and lng
        mMap.addMarker(new MarkerOptions().position(london).title("Marker in LONDON")); //marker is dropped in london
        mMap.moveCamera(CameraUpdateFactory.newLatLng(london)); //move camera to london marker


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {  //if the access for the FINE_LOCATION was granted then if statement is executed
            mMap.setMyLocationEnabled(true);    //creating the current location icon at the top right of the map
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {  //switch statement for permissions
            case MY_PERMISSION_FINE_LOCATION:      //when permission for fine locations is requested then
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);    //creating the current location icon at the top right of the map
                    }
                }else{
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG).show();   //dialog box with alert message
                    finish();
                }
                break;
        }
    }

    public void gotolocation(double lat, double lng, float zoom){
        LatLng ll=new LatLng(lat,lng);  //new latlng object created with value lat and lng passed to it
        CameraUpdate update=CameraUpdateFactory.newLatLngZoom(ll,zoom);
        mMap.moveCamera(update);    //camera is moved to the location of the latitude and longitude that is passed to the function
        mMap.addMarker(new MarkerOptions().position(ll));   //adding marker to the location
    }

    public void geoLocate(View view) throws IOException {
        EditText et= (EditText) findViewById(R.id.editText);    //text that is entered in the text box is saved in the variable et
        String location=et.getText().toString();    //converting the data to string and saving it in a string variable
        Geocoder gc=new Geocoder(this); //new geocoder object created
        List<android.location.Address> list=gc.getFromLocationName(location,1);
        android.location.Address Address=list.get(0);
        String locality=Address.getLocality();

        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();
        double lat=Address.getLatitude();   //latitude saved in double variable
        double lng=Address.getLongitude();  //longitude saved in double variable

        gotolocation(lat,lng,15);   //gotolocation function called with appropriate parameters.

    }
}