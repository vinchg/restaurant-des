package restaurantdes.events;

import restaurantdes.entities.actors.Bartender;
import restaurantdes.entities.actors.Party;

/**
 * Created by Vince on 12/12/2017.
 */
public class PrepareDrinkEvent extends Event {
    public Party p;
    public Bartender bartender;

    public PrepareDrinkEvent(double input_time, Party input_p, Bartender input_bartender) {
        time = input_time;
        p = input_p;
        bartender = input_bartender;
        event_type = EventType.PrepareDrink;
    }
}
