package restaurantdes.events;

import restaurantdes.entities.actors.Host;
import restaurantdes.entities.actors.Party;

/**
 * Created by Vince on 12/11/2017.
 */
// Host seats party then returns to his/her post
public class SeatingEvent extends Event {
    public Party p;
    public Host h;

    public SeatingEvent(double input_time, Party input_p, Host input_h) {
        time = input_time;
        p = input_p;
        h = input_h;
        event_type = EventType.Seating;
    }
}
