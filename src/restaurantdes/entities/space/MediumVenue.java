package restaurantdes.entities.space;

import restaurantdes.entities.Area;

/**
 * Created by Vince on 12/10/2017.
 */
public class MediumVenue extends Venue {

    /*KITCHEN(0),
    BAR(1),
    ENTRANCE(2),
    WAITING_AREA(3),
    DINING(4),
    RESTROOMS(5);*/

    double[][] distance_table = {
            { 0, 10, 20, 24, 14, 10},
            { 10, 0, 10, 14, 8, 16},
            { 20, 10, 0, 4, 10, 16},
            { 24, 14, 4, 0, 14, 20},
            { 14, 8, 10, 14, 0, 6},
            { 10, 16, 16, 20, 6, 0}
    };

    public MediumVenue() {
        kitchen = new Kitchen(16);
        bar = new Bar(16);
        entrance = new Entrance(4);
        waiting_area = new WaitingArea();
        dining = new Dining(20);
        restrooms = new Restrooms(4);
    }

    @Override
    public double distance(Area src, Area dst) {
        return distance_table[src.getIndex()][dst.getIndex()];
    }
}
