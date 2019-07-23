package restaurantdes.events;

import restaurantdes.entities.actors.Party;

/**
 * Created by Vince on 12/11/2017.
 */
// Party arrives and goes into waiting queue
public class PartyArrivalEvent extends Event {
    public Party p;

    public PartyArrivalEvent(double input_time, Party input_p) {
        time = input_time;
        p = input_p;
        event_type = EventType.PartyArrival;
    }
}
