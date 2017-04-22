package com.weride.werideapp;


import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by josephcooper on 07/03/2017.
 */

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
