package com.weride.werideapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.test.espresso.core.deps.guava.net.UrlEscapers;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class CreateRideActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    public static JSONObject route;

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
        mMap.moveCamera(CameraUpdateFactory.zoomTo(9));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }
        mMap.getUiSettings().setAllGesturesEnabled(false);
    }

    public void goToTestActivity(View view){
        String name = getName();
        Calendar dateTime = getDateTime();
        double startLat = getStartLoc().latitude;
        double startLng = getStartLoc().longitude;
        double endLat = getEndLoc().latitude;
        double endLng = getEndLoc().longitude;
        String distance = getDistance();
        int pace = getPace();
        int age = getAge();
        RideInfo rideInfo = new RideInfo(1, name, dateTime, startLat, startLng, endLat, endLng, distance, pace, age);
        System.out.println(rideInfo.infoToString());
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra("rideInfo", rideInfo);
        startActivity(intent);
    }

    public void showRoute(View view) throws IOException{
        String start = ((EditText) findViewById(R.id.startLocEditText)).getText().toString();
        String end = ((EditText) findViewById(R.id.endLocEditText)).getText().toString();
        start = UrlEscapers.urlPathSegmentEscaper().escape(start);
        end = UrlEscapers.urlPathSegmentEscaper().escape(end);
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+start+"&destination="+end+"&avoid=highways&mode=bicycling&key=AIzaSyBdoK6DhGq7aXZOuYgcP3igReXSDlPC9mY";
        parseJson(url);

    }

    public void parseJson(String url) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray routes = response.getJSONArray("routes");
                            route = routes.getJSONObject(0);
                            drawRoute();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        System.out.println(error);
//                        Toast invalidLocation = Toast.makeText(getApplicationContext(), "Please enter valid locations", Toast.LENGTH_SHORT);
//                        invalidLocation.show();
                    }
                });

        queue.add(jsObjRequest);
    }

    public LatLng getStartLoc(){
        try {
            Double startLat = route.getJSONArray("legs").getJSONObject(0).getJSONObject("start_location").getDouble("lat");
            Double startLng = route.getJSONArray("legs").getJSONObject(0).getJSONObject("start_location").getDouble("lng");
            return new LatLng(startLat, startLng);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new LatLng(0, 0);
    }

    public LatLng getEndLoc(){
        try {
            Double endLat = route.getJSONArray("legs").getJSONObject(0).getJSONObject("end_location").getDouble("lat");
            Double endLng = route.getJSONArray("legs").getJSONObject(0).getJSONObject("end_location").getDouble("lng");
            return new LatLng(endLat, endLng);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new LatLng(0, 0);
    }

    public void drawRoute(){
        JSONObject polyline = null;
        String points = "";
        try {
            polyline = route.getJSONObject("overview_polyline");
            points = polyline.getString("points");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<LatLng> pointsList = PolyUtil.decode(points);
        PolylineOptions options = new PolylineOptions();
        options.addAll(pointsList);
        mMap.addPolyline(options);

        LatLng startLoc = getStartLoc();
        LatLng endLoc = getEndLoc();
        mMap.addMarker(new MarkerOptions().position(startLoc));
        mMap.addMarker(new MarkerOptions().position(endLoc));

        LatLngBounds.Builder b = new LatLngBounds.Builder();
        b.include(startLoc);
        b.include(endLoc);
        LatLngBounds bounds = b.build();
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
    }

    public String getName(){
        EditText nameInp = (EditText)findViewById(R.id.raceNameInput);
        return nameInp.getText().toString();
    }

    public Calendar getDateTime(){
        TimePicker timeInp = (TimePicker)findViewById(R.id.startTimePicker);
        DatePicker dateInp = (DatePicker)findViewById(R.id.datePicker);
        int min = timeInp.getMinute();
        int hour = timeInp.getHour();
        int day = dateInp.getDayOfMonth();
        int month = dateInp.getMonth();
        int year = dateInp.getYear();
        Calendar dateTime = Calendar.getInstance();
        dateTime.set(year, month, day, hour, min);
        return dateTime;
    }

    public String getDistance(){
        try {
            return route.getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getString("text");
        } catch (JSONException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return "0 km";
    }

    public int getPace(){
        RatingBar paceBar = (RatingBar)findViewById(R.id.paceBar);
        return (int)paceBar.getRating();
    }

    public int getAge(){
        RadioButton _18_35 = (RadioButton)findViewById(R.id.radioButton18_35);
        RadioButton _36_50 = (RadioButton)findViewById(R.id.radioButton36_50);
        RadioButton _50plus = (RadioButton)findViewById(R.id.radioButton50Plus);
        if (_36_50.isChecked()){
            return 36;
        } else if (_50plus.isChecked()){
            return 51;
        } else {
            return 18;
        }
    }
}
