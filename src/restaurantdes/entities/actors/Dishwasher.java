package restaurantdes.entities.actors;

import restaurantdes.entities.Area;

/**
 * Created by Vince on 12/10/2017.
 */
public class Dishwasher extends Actor {
    public static final double salary = 8;  // Salary per hour

    public Dishwasher(Area input_loc) {
        id = ++counter;
        location = input_loc;
    }
}
