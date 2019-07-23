package restaurantdes.events;

import restaurantdes.entities.actors.Actor;

/**
 * Created by Vince on 12/11/2017.
 */
// Any entity may have to go to the restroom in between events
public class RestroomEvent extends Event {
    public Actor a;

    public RestroomEvent(double input_time, Actor input_a) {
        time = input_time;
        a = input_a;
        event_type = EventType.Restroom;
    }
}
