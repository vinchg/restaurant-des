package restaurantdes.events;

import restaurantdes.entities.actors.Party;
import restaurantdes.entities.actors.Server;

/**
 * Created by Vince on 12/11/2017.
 */
// An available server arrives to take the order and bring it to the kitchen
public class TakeOrderEvent extends Event {
    public Server s;
    public Party p;

    public TakeOrderEvent(double input_time, Party input_p, Server input_s) {
        time = input_time;
        p = input_p;
        s = input_s;
        event_type = EventType.TakeOrder;
    }
}
