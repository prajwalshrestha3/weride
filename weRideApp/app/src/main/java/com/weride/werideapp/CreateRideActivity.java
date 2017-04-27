package com.weride.werideapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.test.espresso.core.deps.guava.net.UrlEscapers;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TimePicker;

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

    private GoogleMap mMap; //GOOGLE MAP OBJECT
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    public static JSONObject route; //STORES JSON OBJECT ROUTE INFORMATION

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ride);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);  //STOP PAGE FROM ROTATING
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.zoomTo(9)); //INITIALLY ZOOM MAP CAMERA TO VALUE OF 9
        //MYLOCATION ENABLED
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }
        mMap.getUiSettings().setAllGesturesEnabled(false);  //DISABLE GESTURES ON MAP
    }

    //GO TO CREATED RIDE ACTIVITY
    public void goToCreatedRide(View view){
        String name = getName();
        Calendar dateTime = getDateTime();
        double startLat = getStartLoc().latitude;
        double startLng = getStartLoc().longitude;
        double endLat = getEndLoc().latitude;
        double endLng = getEndLoc().longitude;
        String distance = getDistance();
        int pace = getPace();
        int age = getAge();

        //IF ANY OF THE RIDEINFO OBJECT VARIABLES ARE NOT SET, I.E. IF A FIELD IN THE CREATE RIDE PAGE HAS NOT HAD DATA ENTERED INTO IT
        if(name=="" ||
                dateTime==null ||
                startLat==0 ||
                startLng==0 ||
                endLat==0 ||
                endLng==0 ||
                distance=="" ||
                pace==0 ||
                age==0)
        {
            EditText editText = (EditText)findViewById(R.id.rideNameInput);
            editText.setError("Please enter a ride name");  //DISPLAY MESSAGE TO USER PROMPTING TO ENTER RIDE NAME
            editText = (EditText)findViewById(R.id.startLocEditText);
            editText.setError("Please enter a start location"); //DISPLAY MESSAGE TO USER PROMPTING TO ENTER START LOCATION
            editText = (EditText)findViewById(R.id.endLocEditText);
            editText.setError("Please enter an end location");  //DISPLAY MESSAGE TO USER PROMPTING TO ENTER END LOCATION
        } else {
            RideInfo rideInfo = new RideInfo(1, name, dateTime, startLat, startLng, endLat, endLng, distance, pace, age);   //CREATE NEW RIDE INFO OBJECT WITH DATA TAKEN FROM FIELDS IN CREATE RIDE PAGE AS PARAMETERS
            Intent intent = new Intent(this, CreatedRide.class);    //INITIALISE A NEW INTENT FOR CREATED RIDE ACTIVITY
            intent.putExtra("rideInfo", rideInfo);  //BIND RIDEINFO OBJECT TO INTENT TO BE USED IN NEW ACTIVITY
            intent.putExtra("startAddress", ((EditText) findViewById(R.id.startLocEditText)).getText().toString()); //BIND START ADDRESS STRING TO INTENT TO BE USED IN NEW ACTIVITY
            intent.putExtra("endAddress", ((EditText) findViewById(R.id.endLocEditText)).getText().toString()); //BIND END ADDRESS STRING TO INTENT TO BE USED IN NEW ACTIVITY
            startActivity(intent);  //START CREATED RIDE ACTIVITY
        }
    }

    //SHOWS POLYLINE AND MARKER ROUTE ON MAP UPON USER CLICKING 'SHOW ROUTE' BUTTON
    public void showRoute(View view) throws IOException{
        //GET EDITTEXT VIEWS FROM PAGE
        EditText start = ((EditText) findViewById(R.id.startLocEditText));
        EditText end = ((EditText) findViewById(R.id.endLocEditText));

        //IF START FIELD IS EMPTY
        if(TextUtils.isEmpty(start.getText().toString())) {
            start.setError("Please enter a start location");    //DISPLAY MESSAGE TO USER PROMPTING TO ENTER START LOCATION
        }

        //IF END FIELD IS EMPTY
        if(TextUtils.isEmpty(end.getText().toString())){
            end.setError("Please enter an end location");   //DISPLAY MESSAGE TO USER PROMPTING TO ENTER END LOCATION
        }

        //IF START AND END FIELDS ARE NOT EMPTY
        if((!TextUtils.isEmpty(start.getText().toString())) && (!TextUtils.isEmpty(end.getText().toString()))){
            //FORMAT START AND END ADDRESS TO BE USED IN URL
            String startURL = UrlEscapers.urlPathSegmentEscaper().escape(start.getText().toString());
            String endURL = UrlEscapers.urlPathSegmentEscaper().escape(end.getText().toString());
            //URL FOR GOOGLE DIRECTIONS API CALL WHICH INCLUDES STARTURL AND END URL AS START AND END LOCATIONS FOR DIRECTIONS SERVICE
            String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + startURL + "&destination=" + endURL + "&avoid=highways&mode=bicycling&key=AIzaSyBdoK6DhGq7aXZOuYgcP3igReXSDlPC9mY";
            parseJson(url); //PASS URL TO DEAL WITH GOOGLE DIRECTION SERVICE CALL
        }
    }

    //PERFORMS JSONOBJECT REQUEST TO RETRIEVE GOOGLE DIRECTION JSON OBJECT TO OBTAIN INFOMATION ABOUT THE DESIRED ROUTE
    public void parseJson(String url) {
        //INSTANTIATE REQUEST QUEUE USING VOLLEY LIBRARY
        RequestQueue queue = Volley.newRequestQueue(this);
        //INSTANTIATE A JSONOBJECTREQUEST WHICH USES THE URL PASSED IN TO GET THE DIRECTIONS JSON
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray routes = response.getJSONArray("routes"); //GET ARRAY WITH KEY VALUE 'ROUTES'
                            route = routes.getJSONObject(0);    //GET FIRST ROUTE
                            drawRoute();    //DRAWS ROUTE TO MAP
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        System.out.println(error);
                    }
                });

        queue.add(jsObjRequest);    //PERFORMS JSONOBJECTREQUEST
    }

    //GETS AND RETURNS START LOCATION LATLNG
    public LatLng getStartLoc(){
        //IF START FIELD IS NOT EMPTY
        if(!TextUtils.isEmpty(((EditText)findViewById(R.id.startLocEditText)).getText().toString())) {
            try {
                //GET THE START LOCATION LAT AND LNG FROM THE RELEVANT PARTS OF THE JSON OBJECT
                Double startLat = route.getJSONArray("legs").getJSONObject(0).getJSONObject("start_location").getDouble("lat");
                Double startLng = route.getJSONArray("legs").getJSONObject(0).getJSONObject("start_location").getDouble("lng");
                return new LatLng(startLat, startLng);  //RETURN THE START LATLNG
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new LatLng(0, 0);    //ELSE RETURN A DEFUALT 0,0 LATLNG
    }

    //GETS AND RETURNS START LOCATION LATLNG
    public LatLng getEndLoc(){
        //IF END FIELD IS NOT EMPTY
        if(!TextUtils.isEmpty(((EditText)findViewById(R.id.endLocEditText)).getText().toString())) {
            try {
                //GET THE END LOCATION LAT AND LNG FROM THE RELEVANT PARTS OF THE JSON OBJECT
                Double endLat = route.getJSONArray("legs").getJSONObject(0).getJSONObject("end_location").getDouble("lat");
                Double endLng = route.getJSONArray("legs").getJSONObject(0).getJSONObject("end_location").getDouble("lng");
                return new LatLng(endLat, endLng);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new LatLng(0, 0);    //ELSE RETURN A DEFUALT 0,0 LATLNG
    }

    //GETS POLYLINE INFORMATION FROM JSON ROUTE OBJECT AND DRAWS MARKERS AND POLYLINE TO MAP
    public void drawRoute(){
        JSONObject polyline = null;
        String points = "";
        try {
            polyline = route.getJSONObject("overview_polyline");    //GET POLYLINE FROM RELEVANT PART OF JSON OBJECT
            points = polyline.getString("points");  //GET THE POLYLINE POINTS STRING FROM RELEVANT PART OF JSON OBJECT

        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<LatLng> pointsList = PolyUtil.decode(points);  //DECODE POLYLINE POINTS STRING AND ASSIGN TO POINTSLIST
        PolylineOptions options = new PolylineOptions();    //CREATE NEW POLYLINEOPTIONS OBJECT
        options.addAll(pointsList); //ADD POLYLINE POINTS TO POLYLINEOPTIONS OBJECT
        mMap.addPolyline(options);  //DRAW POLYLINE TO MAP

        LatLng startLoc = getStartLoc();    //GET START LATLNG
        LatLng endLoc = getEndLoc();    //GET END LATLNG
        mMap.addMarker(new MarkerOptions().position(startLoc)); //DRAW MARKER TO MAP AT START LATLNG
        mMap.addMarker(new MarkerOptions().position(endLoc));   //DRAW MARKER TO MAP AT END LATLNG

        LatLngBounds.Builder b = new LatLngBounds.Builder();    //CREATE NEW LATLNGBOUNDS BUILDER OBJECT
        b.include(startLoc);    //INCLUDE START LOCATION
        b.include(endLoc);  //INCLUDE END LOCATION
        LatLngBounds bounds = b.build();    //BUILD THE LATLNG BOUNDS
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));   //MOVE MAP CAMERA TO DISPLAY ENTIRE ROUTE AND MARKERS
    }

    //GET NAME INPUT
    public String getName(){
        EditText nameInp = (EditText)findViewById(R.id.rideNameInput);
        return nameInp.getText().toString();    //RETURN STRING IN RIDENAMEINPUT
    }

    //GET DATE AND TIME INPUT
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
        return dateTime;    //RETURN CALENDAR OBJECT CONTAINING THE DATE AND TIME SELECTED BY USER
    }

    //GET DISTANCE OF ROUTE FROM JSON OBJECT
    public String getDistance(){
        //IF START AND END LOCATION INPUT FIELDS ARE NOT EMPTY
        if(!TextUtils.isEmpty(((EditText)findViewById(R.id.startLocEditText)).getText().toString()) ||
                (!TextUtils.isEmpty(((EditText)findViewById(R.id.endLocEditText)).getText().toString()))) {
            try {
                return route.getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getString("text"); //RETURN THE DISTANCE TAKEN FROM RELEVANT PART OF JSON OBJECT
            } catch (JSONException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        return "0 km";  //ELSE RETURN DEFAULT OF 0
    }

    //GET PACE OF RIDE
    public int getPace(){
        RatingBar paceBar = (RatingBar)findViewById(R.id.paceBar);
        return (int)paceBar.getRating();    //RETURN PACE OF RIDE SELECTED BY USER
    }

    //GET AGE RANGE FOR RIDE
    public int getAge(){
        RadioButton _18_35 = (RadioButton)findViewById(R.id.radioButton18_35);
        RadioButton _36_50 = (RadioButton)findViewById(R.id.radioButton36_50);
        RadioButton _50plus = (RadioButton)findViewById(R.id.radioButton50Plus);
        //CHECKS WHICH RADIO BUTTON HAS BEEN SELECTED AND RETURNS AN AGE RELATED TO ITS RADIO BUTTON
        if (_36_50.isChecked()){
            return 36;
        } else if (_50plus.isChecked()){
            return 51;
        } else {
            return 18;
        }
    }
}
