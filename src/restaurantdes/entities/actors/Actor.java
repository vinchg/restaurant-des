package restaurantdes.entities.actors;

import restaurantdes.entities.Area;

/**
 * Created by Vince on 12/11/2017.
 */
public class Actor {
    Area location;
    int id;
    static int counter = 0;

    public void moveTo(Area input_loc) {
        location = input_loc;
    }

    public Area getLocation() {
        return location;
    }

    public void setLocation(Area input_location) {
        location = input_location;
    }

    public int getID() {
        return id;
    }
}