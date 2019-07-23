package restaurantdes.entities;

import restaurantdes.entities.actors.Party;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Vince on 12/10/2017.
 */
public class Order {
    final public double TAX_RATE = 1.10;

    List<MenuItem> item_list;
    Party p;
    Random r = new Random();
    double consumption_time;
    double cost;
    double time = 0;
    double cook_time = 0;
    double prepare_drink_time = 0;

    public Order(Party input_p) {
        p = input_p;
        generateOrder();
    }

    public void generateOrder() {
        item_list = new ArrayList<>();
        double temp;
        double max_consumption_time = 0;
        double cooking_time = 0;
        // Generate an list of items for each person
        for(int i = 0; i < p.size(); i++) {
            int num_appetizers, num_entrees, num_drinks, num_desserts;

            temp = r.nextDouble();
            if (temp < 0.4)
                num_appetizers = 0;
            else if (temp < 0.9)
                num_appetizers = 1;
            else
                num_appetizers = 2;

            temp = r.nextDouble();
            if (temp < 0.05)
                num_entrees = 0;
            else if (temp < 0.95)
                num_entrees = 1;
            else
                num_entrees = 2;

            temp = r.nextDouble();
            if (temp < 0.6)
                num_drinks = 1;
            else if (temp < 0.9)
                num_drinks = 2;
            else
                num_drinks = 3;

            temp = r.nextDouble();
            if (temp < 0.4)
                num_desserts = 0;
            else if (temp < 0.9)
                num_desserts = 1;
            else
                num_desserts = 2;

            double consumption_counter = 0;

            for(int j = 0; j < num_appetizers; j++) {
                temp = r.nextDouble();
                if (temp < 0.1)
                    item_list.add(MenuItem.CHICKENWINGS);
                else if (temp < 0.2)
                    item_list.add(MenuItem.EGGROLLS);
                else if (temp < 0.3)
                    item_list.add(MenuItem.GARLICBREAD);
                else if (temp < 0.4)
                    item_list.add(MenuItem.NACHOS);
                else if (temp < 0.6)
                    item_list.add(MenuItem.ONIONRINGS);
                else if (temp < 0.8)
                    item_list.add(MenuItem.SALAD);
                else
                    item_list.add(MenuItem.FRIES);

                consumption_counter += item_list.get(item_list.size() - 1).getConsumption_time();
            }

            for(int j = 0; j < num_entrees; j++) {
                temp = r.nextDouble();
                if (temp < 0.2)
                    item_list.add(MenuItem.SANDWICH);
                else if (temp < 0.45)
                    item_list.add(MenuItem.PIZZA);
                else if (temp < 0.65)
                    item_list.add(MenuItem.BURGER);
                else if (temp < 0.8)
                    item_list.add(MenuItem.STEAK);
                else if (temp < 0.9)
                    item_list.add(MenuItem.RIBS);
                else
                    item_list.add(MenuItem.TACOS);

                consumption_counter += item_list.get(item_list.size() - 1).getConsumption_time();
            }

            for(int j = 0; j < num_drinks; j++) {
                temp = r.nextDouble();
                if (temp < 0.4)
                    item_list.add(MenuItem.WATER);
                else if (temp < 0.45)
                    item_list.add(MenuItem.TEA);
                else if (temp < 0.55)
                    item_list.add(MenuItem.SODA);
                else if (temp < 0.6)
                    item_list.add(MenuItem.JUICE);
                else if (temp < 0.65)
                    item_list.add(MenuItem.MILK);
                else if (temp < 0.75)
                    item_list.add(MenuItem.COFFEE);
                else if (temp < 0.95)
                    item_list.add(MenuItem.BEER);
                else
                    item_list.add(MenuItem.WINE);

                consumption_counter += item_list.get(item_list.size() - 1).getConsumption_time();
            }

            for(int j = 0; j < num_desserts; j++) {
                temp = r.nextDouble();
                if (temp < 0.2)
                    item_list.add(MenuItem.CHOCCAKE);
                else if (temp < 0.5)
                    item_list.add(MenuItem.CHEESECAKE);
                else if (temp < 0.6)
                    item_list.add(MenuItem.BROWNIE);
                else if (temp < 0.9)
                    item_list.add(MenuItem.ICECREAM);
                else
                    item_list.add(MenuItem.COOKIE);

                consumption_counter += item_list.get(item_list.size() - 1).getConsumption_time();
            }

            if (consumption_counter > max_consumption_time)
                max_consumption_time = consumption_counter;
        }

        consumption_time = max_consumption_time;
        computeCostAndCookTime();
    }

    public void computeCostAndCookTime() {
        cost = 0;
        cook_time = 0;
        for(MenuItem i: item_list) {
            cost += i.getPrice();
            if ((13 <= i.getIndex()) && (i.getIndex() <= 20)) {
                prepare_drink_time += i.getPrepareTime();
            }
            else
                cook_time += i.getPrepareTime();
        }
        cost = (cost * TAX_RATE);  // Apply tax
    }

    public List<MenuItem> getOrder() {
        return item_list;
    }

    public MenuItem getItem() {
        return item_list.get(0);
    }

    public boolean isEmpty() {
        return item_list.isEmpty();
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double input) {
        cost = input;
    }

    public double getConsumptionTime() {
        return consumption_time;
    }

    public double getCookingTime() {
        return cook_time;
    }

    public double getPrepareDrinkTime() { return prepare_drink_time; }

    public int size() {
        return item_list.size();
    }

    public void setTime(double input_time) {
        time = input_time;
    }

    public double getTime() {
        return time;
    }
}
