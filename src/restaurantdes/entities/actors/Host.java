package restaurantdes.entities.actors;

import restaurantdes.entities.Area;

/**
 * Created by Vince on 12/10/2017.
 */
public class Host extends Actor {
    public static final double salary = 12;  // Salary per hour

    public Host(Area input_loc) {
        id = ++counter;
        location = input_loc;
    }
}
