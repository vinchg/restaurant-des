package restaurantdes.entities.space;

import restaurantdes.entities.Area;

import java.util.Random;

/**
 * Created by Vince on 12/10/2017.
 */
public abstract class Venue {
    public Kitchen kitchen;
    public Bar bar;
    public Entrance entrance;
    public WaitingArea waiting_area;
    public Dining dining;
    public Restrooms restrooms;

    double[][] distance_table;

    public abstract double distance(Area src, Area dst);
}