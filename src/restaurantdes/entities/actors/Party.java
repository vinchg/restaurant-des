package restaurantdes.entities.actors;

import restaurantdes.entities.Area;
import restaurantdes.entities.Order;

/**
 * Created by Vince on 12/10/2017.
 */
public class Party extends Actor {
    final int size;
    final Area seating_choice;
    double arrival_time;  // When first arrived at restaurant
    double seated_time;  // When seated
    double order_time;  // When order has been finished taken
    double delivery_time;  // When order is delivered
    Order order;


    public Party(int input_size, double input_arrival_time, Area input_seating_choice) {
        id = ++counter;
        size = input_size;
        arrival_time = input_arrival_time;
        seating_choice = input_seating_choice;
        location = Area.WAITING_AREA;
        order = new Order(this);
    }


    // Compute some satisfaction rating between 0 and 1 and return it
    public double satisfaction() {
        double line_wait_time = seated_time - arrival_time;
        double order_taken_time = order_time - seated_time;
        double order_completion_time = delivery_time - order_time;

        if (seated_time == Double.MAX_VALUE)
            return 0.0;

        double total_satisfaction = 0.0;

        if (line_wait_time <= 15)
            total_satisfaction += 1.0;
        else if (line_wait_time > 45)
            total_satisfaction += 0.0;
        else {
            total_satisfaction += (line_wait_time * line_wait_time / -1800.0) + (2025.0 / 1800.0);
        }

        if (order_taken_time <= 10)
            total_satisfaction += 1.0;
        else if (order_taken_time > 30)
            total_satisfaction += 0.0;
        else {
            total_satisfaction += (order_taken_time * order_taken_time / -400.0) + (order_taken_time / 20.0) + (3 / 4);
        }

        if (order_completion_time <= 90)
            total_satisfaction += 1.0;
        else if (order_completion_time > 150)
            total_satisfaction += 0.0;
        else {
            total_satisfaction += ((order_completion_time - 90) * (order_completion_time - 90) / -3600.0) + 1;
        }

        return total_satisfaction/3.0;
    }

    public void setArrivalTime(double time) {
        arrival_time = time;
    }

    public void setSeatedTime(double time) {
        seated_time = time;
    }

    public void setOrderTime(double time) {
        order_time = time;
    }

    public void setDeliveryTime(double time) {
        delivery_time = time;
    }

    public int size() {
        return size;
    }

    public Area getSeatingChoice() {
        return seating_choice;
    }

    public Order getOrder() {
        return order;
    }
}
