package com.weride.werideapp;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney, Australia, and move the camera.
<<<<<<< HEAD
        LatLng london = new LatLng(51.528308, -0.3817822);
        mMap.addMarker(new MarkerOptions().position(london).title("Marker in LONDON"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(london));

=======
//        LatLng london = new LatLng(51.528308, -0.3817822);
//        mMap.addMarker(new MarkerOptions().position(london).title("Marker in LONDON"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(london));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    public void gotolocation(double lat,double lng,float zoom){
        LatLng ll=new LatLng(lat,lng);
        CameraUpdate update=CameraUpdateFactory.newLatLngZoom(ll,zoom);
        mMap.moveCamera(update);
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
>>>>>>> c10fb01f71e4551db4fb4b0fc9287032d9e287b8
    }
}