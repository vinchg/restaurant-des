package restaurantdes.events;

import restaurantdes.entities.actors.Cook;
import restaurantdes.entities.actors.Party;

/**
 * Created by Vince on 12/11/2017.
 */
public class CookingEvent extends Event {
    public Party p;
    public Cook cook;

    public CookingEvent(double input_time, Party input_p, Cook input_cook) {
        time = input_time;
        p = input_p;
        cook = input_cook;
        event_type = EventType.Cooking;
    }
}
