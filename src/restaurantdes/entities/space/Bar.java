package restaurantdes.entities.space;

import restaurantdes.entities.Area;
import restaurantdes.entities.actors.Bartender;
import restaurantdes.entities.actors.Party;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vince on 12/10/2017.
 */
public class Bar {
    //final public Area TYPE = Area.BAR;
    final int MAX_CAPACITY;
    int occupants = 0;
    List<Party> party_list = new ArrayList<>();
    public List<Bartender> bartender_list = new ArrayList<>();

    public Bar(int capacity) {
        MAX_CAPACITY = capacity;
    }

    public boolean addBartender(Bartender b) {
        return bartender_list.add(b);
    }

    public boolean removeParty(Party p_input) {
        boolean r = party_list.remove(p_input);
        if (r)
            occupants -= p_input.size();
        return r;
    }

    public boolean seatParty(Party p_input) {
        if (canSeat(p_input)) {
            party_list.add(p_input);
            occupants += p_input.size();
            return true;
        }
        else
            return false;
    }

    public boolean isEmpty() {
        if (occupants == 0)
            return true;
        else
            return false;
    }

    public boolean canSeat(Party p_input) {
        if ((occupants + p_input.size()) <= MAX_CAPACITY)
            return true;
        else
            return false;
    }
}