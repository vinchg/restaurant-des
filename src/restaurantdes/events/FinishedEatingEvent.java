package restaurantdes.events;

import restaurantdes.entities.actors.Party;

/**
 * Created by Vince on 12/11/2017.
 */
public class FinishedEatingEvent extends Event {
    public Party p;

    public FinishedEatingEvent(double input_time, Party input_p) {
        time = input_time;
        p = input_p;
        event_type = EventType.FinishedEating;
    }
}
