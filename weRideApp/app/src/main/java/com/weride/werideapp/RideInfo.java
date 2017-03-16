package com.weride.werideapp;


import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by josephcooper on 07/03/2017.
 */

public class RideInfo implements Serializable{
    String raceName;
    Calendar dateTime;
    Double startLat, startLng, endLat, endLng;
    int createdByID, pace, age;

    public RideInfo(int createdByID, String raceName, Calendar dateTime, Double startLat, Double startLng, Double endLat, Double endLng, int pace, int age){
        this.createdByID = createdByID;
        this.raceName = raceName;
        this.dateTime = dateTime;
        this.startLat = startLat;
        this.startLng = startLng;
        this.endLat = endLat;
        this.endLng = endLng;
        this.pace = pace;
        this.age = age;
    }

}
