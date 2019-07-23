package restaurantdes;

import restaurantdes.entities.Stats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Vince on 12/10/2017.
 */
public class Console {
    public Console() {
        Scanner s = new Scanner(System.in);
        menu();

        while(true) {
            String line = s.nextLine();
            line.replaceAll("\\s+","");

            if ("1".equals(line)) {
                System.out.print("Restaurant size (s/m/l) = ");
                String size = s.next();  // "s" "m" "l"
                System.out.print("Cooks = ");
                int num_cooks = s.nextInt();
                System.out.print("Bartenders = ");
                int num_bartenders = s.nextInt();
                System.out.print("Hosts = ");
                int num_hosts = s.nextInt();
                System.out.print("Servers = ");
                int num_servers = s.nextInt();
                System.out.print("Dishwashers = ");
                int num_dishwashers = s.nextInt();
                System.out.print("Populartiy = ");
                double populartiy = s.nextDouble();

                Restaurant r = new Restaurant(size, num_cooks, num_bartenders, num_hosts, num_servers, num_dishwashers, populartiy, true, true);
                r.run();
                try {
                    System.in.read();
                } catch (IOException e) {
                }
            } else if ("2".equals(line)) {
                System.out.print("N = ");
                int N = s.nextInt();
                System.out.print("Restaurant size (s/m/l) = ");
                String size = s.next();  // "s" "m" "l"
                System.out.print("Cooks = ");
                int num_cooks = s.nextInt();
                System.out.print("Bartenders = ");
                int num_bartenders = s.nextInt();
                System.out.print("Hosts = ");
                int num_hosts = s.nextInt();
                System.out.print("Servers = ");
                int num_servers = s.nextInt();
                System.out.print("Dishwashers = ");
                int num_dishwashers = s.nextInt();
                System.out.print("Populartiy = ");
                double populartiy = s.nextDouble();

                List<Stats> stats_list = new ArrayList<>();
                Restaurant r = new Restaurant(size, num_cooks, num_bartenders, num_hosts, num_servers, num_dishwashers, populartiy, false, false);
                for(int i = 0; i < N; i++) {
                    r.run();
                    stats_list.add(r.stats);
                }

                // Compute means here
                double total_earnings_avg = 0;
                double average_rating_avg = 0;
                double overtime_hours_avg = 0;
                double total_upkeep_cost_avg = 0;
                double net_profit_avg = 0;
                for(Stats stats : stats_list) {
                    total_earnings_avg += stats.total_earnings;
                    average_rating_avg += stats.average_rating;
                    overtime_hours_avg += stats.overtime_hours;
                    total_upkeep_cost_avg += stats.total_upkeep_cost;
                    net_profit_avg += stats.net_profit;
                }
                total_earnings_avg = total_earnings_avg / N;
                average_rating_avg = average_rating_avg / N;
                overtime_hours_avg = overtime_hours_avg / N;
                total_upkeep_cost_avg = total_upkeep_cost_avg / N;
                net_profit_avg = net_profit_avg / N;

                // Compute standard deviations here
                double total_earnings_sd = 0;
                double average_rating_sd = 0;
                double overtime_hours_sd = 0;
                double total_upkeep_cost_sd = 0;
                double net_profit_sd = 0;
                for(Stats stats : stats_list) {
                    total_earnings_sd += Math.pow((stats.total_earnings - total_earnings_avg), 2);
                    average_rating_sd += Math.pow((stats.average_rating - average_rating_avg), 2);
                    overtime_hours_sd += Math.pow((stats.overtime_hours - overtime_hours_avg), 2);
                    total_upkeep_cost_sd += Math.pow((stats.total_upkeep_cost - total_upkeep_cost_avg), 2);
                    net_profit_sd += Math.pow((stats.net_profit - net_profit_avg), 2);
                }
                total_earnings_sd = Math.sqrt(total_earnings_sd / N);
                average_rating_sd = Math.sqrt(average_rating_sd / N);
                overtime_hours_sd = Math.sqrt(overtime_hours_sd / N);
                total_upkeep_cost_sd = Math.sqrt(total_upkeep_cost_sd / N);
                net_profit_sd = Math.sqrt(net_profit_sd / N);

                System.out.println("\nTotal Earnings | Mean: " + total_earnings_avg + "   SD: " + total_earnings_sd);
                System.out.println("Average Rating | Mean: " + average_rating_avg + "   SD: " + average_rating_sd);
                System.out.println("Overtime Hours | Mean: " + overtime_hours_avg + "   SD: " + overtime_hours_sd);
                System.out.println("Total Upkeep Cost | Mean: " + total_upkeep_cost_avg + "   SD: " + total_upkeep_cost_sd);
                System.out.println("Net Profit | Mean: " + net_profit_avg + "   SSD: " + net_profit_sd);

                try {
                    System.in.read();
                } catch (IOException e) {
                }
            } else if ("3".equals(line)) {
                System.out.print("N = ");
                int N = s.nextInt();
                System.out.print("Restaurant size (s/m/l) = ");
                String size = s.next();  // "s" "m" "l"
                System.out.print("Cooks = ");
                int num_cooks = s.nextInt();
                System.out.print("Bartenders = ");
                int num_bartenders = s.nextInt();
                System.out.print("Hosts = ");
                int num_hosts = s.nextInt();
                System.out.print("Servers = ");
                int num_servers = s.nextInt();
                System.out.print("Dishwashers = ");
                int num_dishwashers = s.nextInt();
                System.out.print("Populartiy = ");
                double populartiy = s.nextDouble();

                Restaurant r = new Restaurant(size, num_cooks, num_bartenders, num_hosts, num_servers, num_dishwashers, populartiy, true, true);
                r.simple = true;
                r.n_arrivals = N;
                r.run();
                try {
                    System.in.read();
                } catch (IOException e) {
                }
            } else if ("menu".equalsIgnoreCase(line)) {
                menu();
            } else if ("quit".equalsIgnoreCase(line)) {
                return;
            } else {
                System.out.println();
                menu();
            }
        }
    }

    private void menu() {
        System.out.println("----Restaurant Discrete Event Simulation----");
        System.out.println(" 1 | Simulate");
        System.out.println(" 2 | Simulate N times");
        System.out.println(" 3 | Test N parties");
        System.out.println(" menu | Print Menu");
        System.out.println(" quit | Quit");
        System.out.println("----------------------------------------------------------------");
    }
}
