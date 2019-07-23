package restaurantdes.entities.space;

import restaurantdes.entities.actors.Party;

/**
 * Created by Vince on 12/10/2017.
 */
public class Dining {
    final int MAX_TABLES;
    Table[] tables;
    int occupied_tables = 0;

    public Dining(int num_tables) {
        tables = new Table[num_tables];
        for(int i = 0; i < num_tables; i++) {
            tables[i] = new Table();
        }
        MAX_TABLES = num_tables;
    }

    public int availableTables() {
        return (MAX_TABLES - occupied_tables);
    }

    public Table getEmptyTable() {
        for(int i = 0; i < tables.length; i++) {
            if (tables[i].isEmpty())
                return tables[i];
        }
        return null;
    }
    
    public boolean canSeat(Party p_input) {
        int number_tables_required = (int) Math.ceil(p_input.size() / Table.MAX_CAPACITY);
        if (availableTables() >= number_tables_required)
            return true;
        else
            return false;
    }

    public boolean seatParty(Party p_input) {
        int number_tables_required = (int) Math.ceil(p_input.size() / Table.MAX_CAPACITY);
        if (availableTables() >= number_tables_required) {
            for(int i = 0; i < number_tables_required; i++) {
                getEmptyTable().seatTable(p_input);
                occupied_tables++;
            }
            return true;
        }
        else
            return false;
    }

    public boolean removeParty(Party p_input) {
        boolean result = false;
        for(int i = 0; i < tables.length; i++) {
            if (tables[i].getParty() == p_input) {
                tables[i].clearTable();
                occupied_tables--;
                result = true;
            }
        }
        return result;
    }
}