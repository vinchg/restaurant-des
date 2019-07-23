package restaurantdes.events;

import restaurantdes.entities.actors.Party;
import restaurantdes.entities.actors.Server;

/**
 * Created by Vince on 12/11/2017.
 */
// An available server comes and cleans the table, then proceeds to take the dishes to the kitchen and add them to the pile
public class CleanTableEvent extends Event {
    public Party p;
    public Server s;

    public CleanTableEvent(double input_time, Party input_p, Server input_s) {
        time = input_time;
        p = input_p;
        s = input_s;
        event_type = EventType.CleanTable;
    }
}
