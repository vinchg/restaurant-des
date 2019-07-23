package restaurantdes.entities.space;

import restaurantdes.entities.Area;

/**
 * Created by Vince on 12/10/2017.
 */
public class SmallVenue extends Venue {

  /*KITCHEN(0),
    BAR(1),
    ENTRANCE(2),
    WAITING_AREA(3),
    DINING(4),
    RESTROOMS(5);*/

    double[][] distance_table = {
            { 0, 5, 10, 12, 7, 5},
            { 5, 0, 5, 7, 4, 8},
            { 10, 5, 0, 2, 5, 8},
            { 12, 7, 2, 0, 7, 10},
            { 7, 4, 5, 7, 0, 3},
            { 5, 8, 8, 10, 3, 0}
    };

    public SmallVenue() {
        kitchen = new Kitchen(8);
        bar = new Bar(8);
        entrance = new Entrance(2);
        waiting_area = new WaitingArea();
        dining = new Dining(10);
        restrooms = new Restrooms(2);
    }

    @Override
    public double distance(Area src, Area dst) {
        return distance_table[src.getIndex()][dst.getIndex()];
    }
}
