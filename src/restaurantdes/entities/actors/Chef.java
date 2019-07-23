package restaurantdes.entities.actors;

import restaurantdes.entities.Area;

/**
 * Created by Vince on 12/10/2017.
 */
public class Chef extends Actor {
    public static final double salary = 30;  // Salary per hour

    public Chef(Area input_loc) {
        id = ++counter;
        location = input_loc;
    }
}