package restaurantdes.events;

import restaurantdes.entities.actors.Dishwasher;
import restaurantdes.entities.actors.Party;

/**
 * Created by Vince on 12/11/2017.
 */
public class DishwashingEvent extends Event {
    public Party p;
    public Dishwasher d;

    public DishwashingEvent(double input_time, Party input_p, Dishwasher input_d) {
        time = input_time;
        p = input_p;
        d = input_d;
        event_type = EventType.Dishwashing;
    }
}
