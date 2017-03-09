package com.weride.werideapp;

import android.location.Location;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by josephcooper on 07/03/2017.
 */

public class RideInfo implements Serializable{
    String raceName;
    Calendar dateTime;
    Location startLoc, endLoc;
    int pace, age;

    public RideInfo(String name, Calendar startDateTime){
        raceName = name;
        dateTime = startDateTime;
//        startLoc = start;
//        endLoc = end;
//        pace = racePace;
//        age = riderAge;
    }

}
