import android.location.Location;

import java.util.Date;

/**
 * Created by josephcooper on 07/03/2017.
 */

public class RideInfo {
    String raceName;
    Location startLoc, endLoc;
    Date dateTime;
    int pace, age;

    public RideInfo(String name, Location start, Location end, Date date, int racePace, int riderAge){
        raceName = name;
        startLoc = start;
        endLoc = end;
        dateTime = date;
        pace = racePace;
        age = riderAge;
    }

}
