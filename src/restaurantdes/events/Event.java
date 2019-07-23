package restaurantdes.events;

/**
 * Created by Vince on 12/10/2017.
 */
public class Event implements Comparable<Event> {
    double time = 0;
    EventType event_type;


    public int compareTo(Event e) {
        if (this.time == e.time)
            return 0;
        else
            return this.time > e.time ? 1 : -1;
    }

    public EventType getEventType() {
        return event_type;
    }

    public int getEventTypeIndex() {
        return event_type.getIndex();
    }

    public double getTime() {
        return time;
    }
}
