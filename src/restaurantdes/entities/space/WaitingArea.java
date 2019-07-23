package restaurantdes.entities.space;

import restaurantdes.entities.actors.Party;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vince on 12/10/2017.
 */
public class WaitingArea {
    List<Party> party_list = new ArrayList<>();

    public void addParty(Party p) {
        party_list.add(p);
    }

    public boolean removeParty(Party p) {
        return party_list.remove(p);
    }

    public int lineSize() {
        return party_list.size();
    }
}
