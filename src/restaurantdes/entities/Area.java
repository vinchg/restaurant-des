package restaurantdes.entities;

/**
 * Created by Vince on 12/10/2017.
 */
public enum Area {
    KITCHEN(0),
    BAR(1),
    ENTRANCE(2),
    WAITING_AREA(3),
    DINING(4),
    RESTROOMS(5);

    private int index;

    Area(int input_index) {
        index = input_index;
    }

    public int getIndex() {
        return index;
    }
}