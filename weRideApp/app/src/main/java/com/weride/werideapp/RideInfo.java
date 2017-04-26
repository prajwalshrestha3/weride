package com.weride.werideapp;


import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by josephcooper on 07/03/2017.
 */

//CLASS THAT ALLOWS RIDES TO BE STORED IN A MANAGEABLE WAY, FOR EASY MANIPULATION WITHIN APP AND IN SENDING AND RECEIVING TO AND FROM DATABASE
public class RideInfo implements Serializable{
    String rideName, distance;
    Calendar dateTime;
    double startLat, startLng, endLat, endLng;
    int createdByID, pace, age;

    public RideInfo(int createdByID, String rideName, Calendar dateTime, double startLat, double startLng, double endLat, double endLng, String distance, int pace, int age){
        this.createdByID = createdByID;
        this.rideName = rideName;
        this.dateTime = dateTime;
        this.startLat = startLat;
        this.startLng = startLng;
        this.endLat = endLat;
        this.endLng = endLng;
        this.distance = distance;
        this.pace = pace;
        this.age = age;
    }

    //FORMATS ALL RIDEINFO OBJECT VARIABLES INTO A SINGLE STRING TO BE USED IN THE URL THATS USED IN THE PROCESS OF SENDING RIDE DATA TO THE DATABASE
    public String infoToString(){
        return this.createdByID + ", " +
                this.rideName + ", " +
                this.dateTime.getTime() + ", " +
                this.startLat + ", " +
                this.startLng + ", " +
                this.endLat + ", " +
                this.endLng + ", " +
                this.distance + ", " +
                this.pace + ", " +
                this.age;
    }

}
