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

    private GoogleMap mMap;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        LatLng london = new LatLng(51.528308, -0.3817822);
        mMap.addMarker(new MarkerOptions().position(london).title("Marker in LONDON"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(london));


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }
                }else{
                    Toast.makeText(this, "FUCK OFF!!", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    public void gotolocation(double lat, double lng, float zoom){
        LatLng ll=new LatLng(lat,lng);
        CameraUpdate update=CameraUpdateFactory.newLatLngZoom(ll,zoom);
        mMap.moveCamera(update);
        mMap.addMarker(new MarkerOptions().position(ll));
    }

    public void geoLocate(View view) throws IOException {
        EditText et= (EditText) findViewById(R.id.editText);
        String location=et.getText().toString();
        Geocoder gc=new Geocoder(this);
        List<android.location.Address> list=gc.getFromLocationName(location,1);
        android.location.Address Address=list.get(0);
        String locality=Address.getLocality();

        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();
        double lat=Address.getLatitude();
        double lng=Address.getLongitude();

        gotolocation(lat,lng,15);

    }
}