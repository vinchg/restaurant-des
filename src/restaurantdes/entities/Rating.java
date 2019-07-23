package restaurantdes.entities;

import restaurantdes.entities.actors.Party;

/**
 * Created by Vince on 12/12/2017.
 */
public class Rating {
    public Party p;
    public double rating = 0.0;

    public Rating(Party input_p) {
        p = input_p;
        rating = 5*p.satisfaction();
    }
}
