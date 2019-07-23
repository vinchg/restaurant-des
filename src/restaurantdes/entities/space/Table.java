package restaurantdes.entities.space;

import restaurantdes.entities.actors.Party;

/**
 * Created by Vince on 12/10/2017.
 */
public class Table {
    final static int MAX_CAPACITY = 4;
    int occupants = 0;
    Party p;

    public void clearTable() {
        p = null;
        occupants = 0;
    }

    public void seatTable(Party p_input) {
        p = p_input;
        occupants = p.size();
    }

    public boolean isEmpty() {
        if (occupants == 0)
            return true;
        else
            return false;
    }

    public Party getParty() {
        return p;
    }
}
