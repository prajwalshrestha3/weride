package com.weride.werideapp;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreateRaceActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_race);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng london = new LatLng(51.528308, -0.3817822);
        LatLng brighton = new LatLng(50.8225, 0.1372);
        mMap.addMarker(new MarkerOptions().position(london).title("Marker in LONDON"));
        mMap.addMarker(new MarkerOptions().position(brighton).title("Marker in Brighton"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(london));

    }

//    SeekBar seekBar = (SeekBar)findViewById(R.id.ageSeekBar);


    
}
