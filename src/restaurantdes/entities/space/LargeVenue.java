package restaurantdes.entities.space;

import restaurantdes.entities.Area;

/**
 * Created by Vince on 12/10/2017.
 */
public class LargeVenue extends Venue {

    /*KITCHEN(0),
    BAR(1),
    ENTRANCE(2),
    WAITING_AREA(3),
    DINING(4),
    RESTROOMS(5);*/

    double[][] distance_table = {
            { 0, 20, 40, 48, 28, 20},
            { 20, 0, 20, 28, 16, 32},
            { 40, 20, 0, 8, 20, 32},
            { 48, 28, 8, 0, 28, 40},
            { 28, 16, 20, 28, 0, 12},
            { 20, 32, 32, 40, 12, 0}
    };

    public LargeVenue() {
        kitchen = new Kitchen(24);
        bar = new Bar(24);
        entrance = new Entrance(8);
        waiting_area = new WaitingArea();
        dining = new Dining(40);
        restrooms = new Restrooms(8);
    }

    @Override
    public double distance(Area src, Area dst) {
        return distance_table[src.getIndex()][dst.getIndex()];
    }
}
