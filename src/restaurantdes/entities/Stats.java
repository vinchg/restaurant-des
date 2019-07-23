package restaurantdes.entities;

/**
 * Created by Vince on 12/12/2017.
 */
public class Stats {
    final public double running_time;
    final public double total_earnings;
    final public int customers_served;
    final public double average_rating;

    final public double cost_per_hour;
    final public double normal_hours;
    final public double normal_cost;
    final public double overtime_hours;
    final public double overtime_cost;
    final public double total_upkeep_cost;
    final public double net_profit;

    public Stats(double i_running_time,
                 double i_total_earnings,
                 int i_customers_served,
                 double i_average_rating,
                 double i_cost_per_hour,
                 double i_normal_hours,
                 double i_normal_cost,
                 double i_overtime_hours,
                 double i_overtime_cost,
                 double i_total_upkeep_cost,
                 double i_net_profit) {
        running_time = i_running_time;
        total_earnings = i_total_earnings;
        customers_served = i_customers_served;
        average_rating = i_average_rating;
        cost_per_hour = i_cost_per_hour;
        normal_hours = i_normal_hours;
        normal_cost = i_normal_cost;
        overtime_hours = i_overtime_hours;
        overtime_cost = i_overtime_cost;
        total_upkeep_cost = i_total_upkeep_cost;
        net_profit = i_net_profit;
    }
}
