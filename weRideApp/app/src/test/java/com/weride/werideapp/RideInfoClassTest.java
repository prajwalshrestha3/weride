package com.weride.werideapp;

import org.junit.Before;
import org.junit.Test;
import com.weride.werideapp.RideInfo;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Tests constructor and infoToString methods of RideInfo class
 */

public class RideInfoClassTest {

    @Test
    public void constructor() throws Exception {
        RideInfo testInfo = new RideInfo(1, "tester", Calendar.getInstance(), 52, 52.3, 48.3, 49.5, "12 km", 3, 18);    //CREATE NEW RIDEINFO OBJECT FOR TEST PURPOSES WITH DUMMY PARAMETERS
        assertNotNull("Constructor is not working, object not created.", testInfo); //CHECK THAT THE CONSTRCUTOR WORKS AND A NEW OBJECT WAS CREATED I.E. CHECK THAT TESTINFO ISNT NULL
    }

    @Test
    public void infoToStringMethod(){
        Calendar calendar = Calendar.getInstance(); //GET AN INSTANCE OF CALENDAR OBJECT
        RideInfo testInfo = new RideInfo(1, "tester", calendar, 52, 52.3, 48.3, 49.5, "12 km", 3, 18);  //CREATE NEW RIDEINFO OBJECT FOR TEST PURPOSES WITH DUMMY PARAMETERS
        String infoString = "1, tester, " + calendar.getTime() + ", 52.0, 52.3, 48.3, 49.5, 12 km, 3, 18";  //INITIALISE A NEW STRING STORING THE EXPECTED OUTPUT OF THE INFOTOSTRING METHOD
        assertNotNull("Method does not return a string object.", testInfo.infoToString());  //CHECK THAT THE METHOD RETURNS A STRING OBJECT, NOT NULL
        assertEquals("String returned is incorrect", infoString, testInfo.infoToString());  //CHECK THAT THE STRING RETURNED IS THE SAME AS THE EXPECTED STRING
    }
}