package restaurantdes.entities.space;

import restaurantdes.entities.actors.Chef;
import restaurantdes.entities.actors.Cook;
import restaurantdes.entities.actors.Dishwasher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vince on 12/10/2017.
 */
public class Kitchen {
    final int MAX_CAPACITY;
    int occupancy = 0;
    public Chef c;
    public List<Cook> cook_list;
    public List<Dishwasher> dishwasher_list;
    int dishes;

    public Kitchen(int capacity) {
        MAX_CAPACITY = capacity;
    }

    public boolean addChef(Chef input_c) {
        if (occupancy + 1 <= MAX_CAPACITY) {
            occupancy++;
            c = input_c;
            return true;
        }
        else
            return false;
    }

    public boolean addCook(Cook input_cook) {
        if (occupancy + 1 <= MAX_CAPACITY) {
            occupancy++;
            cook_list.add(input_cook);
            return true;
        }
        else
            return false;
    }

    public boolean addDishwasher(Dishwasher input_dishwasher) {
        if (occupancy + 1 <= MAX_CAPACITY) {
            occupancy++;
            dishwasher_list.add(input_dishwasher);
            return true;
        }
        else
            return false;
    }

    public void addDishes(int number) {
        dishes += number;
    }

    public boolean removeDishes(int number) {
        if (dishes - number >= 0) {
            dishes -= number;
            return true;
        }
        else
            return false;
    }
}