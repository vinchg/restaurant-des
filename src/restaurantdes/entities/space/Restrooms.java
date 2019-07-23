package restaurantdes.entities.space;

import restaurantdes.entities.actors.Party;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vince on 12/10/2017.
 */
public class Restrooms {
    final int MAX_CAPACITY;
    int occupancy = 0;
    List<Party> party_list = new ArrayList<>();

    public Restrooms(int capacity) {
        MAX_CAPACITY = capacity;
    }

    public boolean canFit(Party p) {
        if (p.size() + occupancy <= MAX_CAPACITY)
            return true;
        else
            return false;
    }

    public boolean addParty(Party p) {
        if (canFit(p)) {
            occupancy += party_list.size();
            party_list.add(p);
            return true;
        }
        else
            return false;
    }

    public boolean removeParty(Party p) {
        boolean r = party_list.remove(p);
        if (r)
            occupancy -= party_list.size();
        return r;
    }
}
