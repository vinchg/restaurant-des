package restaurantdes.events;

import restaurantdes.entities.actors.Party;
import restaurantdes.entities.actors.Server;

/**
 * Created by Vince on 12/11/2017.
 */
public class OrderDeliveryEvent extends Event {
    public Server s;
    public Party p;

    public OrderDeliveryEvent(double input_time, Party input_p, Server input_s) {
        time = input_time;
        p = input_p;
        s = input_s;
        event_type = EventType.OrderDelivery;
    }
}
