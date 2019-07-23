package restaurantdes.entities.actors;

import restaurantdes.entities.Area;

/**
 * Created by Vince on 12/10/2017.
 */
public class Cook extends Actor {
    public static final double salary = 15;  // Salary per hour

    public Cook(Area input_loc) {
        id = ++counter;
        location = input_loc;
    }
}
