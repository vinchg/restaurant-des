package restaurantdes.events;

import restaurantdes.entities.actors.Host;
import restaurantdes.entities.actors.Party;

/**
 * Created by Vince on 12/11/2017.
 */
public class PaymentEvent extends Event {
    public Host h;
    public Party p;

    public PaymentEvent(double input_time, Party input_p, Host input_h) {
        time = input_time;
        p = input_p;
        h = input_h;
        event_type = EventType.Payment;
    }
}
