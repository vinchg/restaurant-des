package restaurantdes.entities.actors;

import restaurantdes.entities.Area;

/**
 * Created by Vince on 12/10/2017.
 */
public class Bartender extends Actor {
    public static final double salary = 15;  // Salary per hour

    public Bartender(Area input_loc) {
        id = ++counter;
        location = input_loc;
    }
}
