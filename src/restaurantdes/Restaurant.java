package restaurantdes;

import restaurantdes.entities.*;
import restaurantdes.entities.space.*;
import restaurantdes.entities.actors.*;
import restaurantdes.events.*;

import java.util.*;

/**
 * Created by Vince on 12/10/2017.
 */
public class Restaurant {
    Random r = new Random();

    final double closing_time = 600.0;
    double time = 0.0;  // Minutes
    double actual_finish_time;

    // Default values
    Venue venue = new SmallVenue();
    final double RATE_OF_WALKING = 85; // 85 meters per minute
    int num_cooks = 2;
    int num_bartenders = 1;
    int num_hosts = 1;
    int num_servers = 1;
    int num_dishwashers = 1;
    double populartiy = 1.0;
    boolean verbose = true;
    boolean result = true;

    public boolean simple = false;
    public int n_arrivals = 0;

    // Event Priority Queue
    PriorityQueue<Event> event_pq = new PriorityQueue<>();

    // Entities
    List<Bartender> bartender_idle_list = new ArrayList<>();
    List<Cook> cooks_idle_list = new ArrayList<>();
    List<Dishwasher> dishwasher_idle_list = new ArrayList<>();
    List<Host> host_idle_list = new ArrayList<>();
    List<Server> server_idle_list = new ArrayList<>();

    // Party states
    List<Party> dining_wait_list = new ArrayList<>();
    List<Party> bar_wait_list = new ArrayList<>();
    List<Party> seated_list = new ArrayList<>();
    List<Party> ordered_waiting_list = new ArrayList<>();
    List<Party> eating_list = new ArrayList<>();
    List<Party> finished_eating_list = new ArrayList<>();

    // Employee States
    List<Party> cook_list = new ArrayList<>();
    List<Party> finished_cook_list = new ArrayList<>();
    List<Party> drink_list = new ArrayList<>();
    List<Party> finished_drink_list = new ArrayList<>();
    List<Party> dirty_table_list = new ArrayList<>();
    List<Party> dishes_list = new ArrayList<>();

    // Rating List
    List<Rating> rating_list = new ArrayList<>();

    // Statistics
    public Stats stats = null;


    public Restaurant(String size, int i_num_cooks, int i_num_bartenders, int i_num_hosts, int i_num_servers, int i_num_dishwashers, double i_populartiy, boolean i_verbose, boolean i_result) {
        if (size.equalsIgnoreCase("s"))
            venue = new SmallVenue();
        else if (size.equalsIgnoreCase("m"))
            venue = new MediumVenue();
        else if (size.equalsIgnoreCase("l"))
            venue = new LargeVenue();

        num_cooks = i_num_cooks;
        num_bartenders = i_num_bartenders;
        num_hosts = i_num_hosts;
        num_servers = i_num_servers;
        num_dishwashers = i_num_dishwashers;
        populartiy = i_populartiy;
        verbose = i_verbose;
        result = i_result;
    }

    public void run() {
        initialize();

        while(!event_pq.isEmpty()){
            Event e = event_pq.poll();
            time = e.getTime();

            if (time > closing_time)  // If it's past closing time, then empty the lines
                emptyWaitLists();

            switch(e.getEventTypeIndex()) {
                case 0:
                    handlePartyArrivalEvent((PartyArrivalEvent) e);
                    break;
                case 1:
                    handleSeatingEvent((SeatingEvent) e);
                    break;
                case 2:
                    handleTakeOrderEvent((TakeOrderEvent) e);
                    break;
                case 3:
                    handleOrderDeliveryEvent((OrderDeliveryEvent) e);
                    break;
                case 4:
                    handleFoodDeliveryEvent((FoodDeliveryEvent) e);
                    break;
                case 5:
                    handleFinishedEatingEvent((FinishedEatingEvent) e);
                    break;
                case 6:
                    handlePaymentEvent((PaymentEvent) e);
                    break;
                case 7:
                    handleCleanTableEvent((CleanTableEvent) e);
                    break;
                case 8:
                    handleDishesDeliveryEvent((DishesDeliveryEvent) e);
                    break;
                case 9:
                    handleRestroomEvent((RestroomEvent) e);
                    break;
                case 10:
                    handleCookingEvent((CookingEvent) e);
                    break;
                case 11:
                    handleDishwashingEvent((DishwashingEvent) e);
                    break;
                case 12:
                    handlePrepareDrinkEvent((PrepareDrinkEvent) e);
                    break;
                case 13:
                    handleDrinkDeliveryEvent((DrinkDeliveryEvent) e);
                    break;
                default:
                    System.out.println("ERROR: Invalid Event");
                    break;
            }

            checkStates();  // Mostly responsible for repopulating the event pq.
        }

        if (closing_time < time)
            actual_finish_time = time;
        else
            actual_finish_time = closing_time;

        if (verbose)
            System.out.println(Math.round(time * 100.0) / 100.0 + ": END.\n");

        compileStats();
        if (result)
            displayStats();
    }

    private void initialize() {
        event_pq = new PriorityQueue<>();

        // Entities
        bartender_idle_list = new ArrayList<>();
        cooks_idle_list = new ArrayList<>();
        dishwasher_idle_list = new ArrayList<>();
        host_idle_list = new ArrayList<>();
        server_idle_list = new ArrayList<>();

        // Party states
        dining_wait_list = new ArrayList<>();
        bar_wait_list = new ArrayList<>();
        seated_list = new ArrayList<>();
        ordered_waiting_list = new ArrayList<>();
        eating_list = new ArrayList<>();
        finished_eating_list = new ArrayList<>();

        // Employee States
        cook_list = new ArrayList<>();
        finished_cook_list = new ArrayList<>();
        drink_list = new ArrayList<>();
        finished_drink_list = new ArrayList<>();
        dirty_table_list = new ArrayList<>();
        dishes_list = new ArrayList<>();

        // Rating List
        rating_list = new ArrayList<>();

        // Statistics
        stats = null;


        // Add events
        if (simple)
            generateSimplePartyArrivals();
        else
            generatePartyArrivals();

        // Add entities
        bartender_idle_list.add(new Bartender(Area.BAR));  // Always add only one bartender
        cooks_idle_list.add(new Cook(Area.KITCHEN));  // Always add one cook
        dishwasher_idle_list.add(new Dishwasher(Area.KITCHEN));  // Always only add one dishwasher
        for(int i = 0; i < num_hosts; i++)
            host_idle_list.add(new Host(Area.ENTRANCE));
        for(int i = 0; i < num_servers; i++)
            server_idle_list.add(new Server(Area.DINING));
    }

    public void generatePartyArrivals() {
        double temp;
        for(double time = 0.0; time < closing_time;) {
            temp = getExp(time, populartiy);
            while (temp <= 0) {
                temp = getExp(time, populartiy);
            }
            time += temp;  // Adjust new time appropriately

            if (time > closing_time)
                break;

            // Generate Party Size
            double prob = r.nextDouble();
            int party_size;
            if (prob < 0.05)
                party_size = 1;
            else if (prob < 0.25)
                party_size = 2;
            else if (prob < 0.5)
                party_size = 3;
            else if (prob < 0.8)
                party_size = 4;
            else if (prob < 0.85)
                party_size = 5;
            else if (prob < 0.9)
                party_size = 6;
            else if (prob < 0.95)
                party_size = 7;
            else
                party_size = 8;

            // Generate Seating area
            prob = r.nextDouble();
            Area area;
            if (prob < 0.7)
                area = Area.DINING;
            else
                area = Area.BAR;

            event_pq.add(new PartyArrivalEvent(time, new Party(party_size, time, area)));
        }
        if (verbose)
            displayArrivalDistribution();
    }

    public void generateSimplePartyArrivals() {
        int counter = 0;
        double temp;
        for(double time = 0.0; time < closing_time;) {
            if (counter >= n_arrivals)
                break;

            temp = getExp(time, populartiy);
            while (temp <= 0) {
                temp = getExp(time, populartiy);
            }
            time += temp;  // Adjust new time appropriately

            if (time > closing_time)
                break;

            // Generate Party Size
            double prob = r.nextDouble();
            int party_size;
            if (prob < 0.05)
                party_size = 1;
            else if (prob < 0.25)
                party_size = 2;
            else if (prob < 0.5)
                party_size = 3;
            else if (prob < 0.8)
                party_size = 4;
            else if (prob < 0.85)
                party_size = 5;
            else if (prob < 0.9)
                party_size = 6;
            else if (prob < 0.95)
                party_size = 7;
            else
                party_size = 8;

            // Generate Seating area
            prob = r.nextDouble();
            Area area;
            if (prob < 0.7)
                area = Area.DINING;
            else
                area = Area.BAR;

            event_pq.add(new PartyArrivalEvent(time, new Party(party_size, time, area)));
            counter++;
        }
        if (verbose)
            displayArrivalDistribution();
    }

    public void displayArrivalDistribution() {
        int[] counter = new int[10];
        int index;
        for(Event e : event_pq) {
            index = (int) Math.floor(e.getTime() / 60.0);
            counter[index]++;
        }

        for(int i = 0; i < counter.length; i++) {
            System.out.print(counter[i] + " ");
        }
        System.out.println();
    }


    private void handlePartyArrivalEvent(PartyArrivalEvent e) {
        Area seating_choice = e.p.getSeatingChoice();

        // Update states
        if (seating_choice == Area.DINING) {
            dining_wait_list.add(e.p);
        } else if (seating_choice == Area.BAR) {
            bar_wait_list.add(e.p);
        }

        if (verbose)
            System.out.println(Math.round(time * 100.0) / 100.0 + ": PARTY " + e.p.getID() + " has been added to the " + seating_choice + " wait list.");
    }

    private void handleSeatingEvent(SeatingEvent e) {
        Area seating_choice = e.p.getSeatingChoice();

        // Update locations
        e.p.setLocation(seating_choice);
        e.h.setLocation(seating_choice);

        // Update states
        if (seating_choice == Area.DINING)
            venue.dining.seatParty(e.p);
        else if (seating_choice == Area.BAR)
            venue.bar.seatParty(e.p);
        e.p.setSeatedTime(time);
        seated_list.add(e.p);

        // Update entities
        host_idle_list.add(e.h);

        // Print if necessary
        if (verbose)
            System.out.println(Math.round(time * 100.0) / 100.0 + ": PARTY " + e.p.getID() + " has been seated in the " + seating_choice + " area.");
    }

    private void handleTakeOrderEvent(TakeOrderEvent e) {
        // Update locations
        e.s.setLocation(e.p.getLocation());

        // Update states
        e.p.getOrder().setTime(time);
        e.p.setOrderTime(time);
        ordered_waiting_list.add(e.p);

        // After taking the order, delivery the order
        double est_time = time + computeTravelTime(venue.distance(e.s.getLocation(), Area.KITCHEN));
        event_pq.add(new OrderDeliveryEvent(est_time, e.p, e.s));

        // Print if necessary
        if (verbose)
            System.out.println(Math.round(time * 100.0) / 100.0 + ": SERVER " + e.s.getID() + " has taken an order for PARTY " + e.p.getID() + " in the " + e.p.getLocation() + " area.");
    }

    // Delivers order to kitchen
    private void handleOrderDeliveryEvent(OrderDeliveryEvent e) {
        // Update locations
        e.s.setLocation(Area.KITCHEN);

        // Update states
        cook_list.add(e.p);
        drink_list.add(e.p);

        // Update entities
        server_idle_list.add(e.s);

        // Print if necessary
        if (verbose)
            System.out.println(Math.round(time * 100.0) / 100.0 + ": SERVER " + e.s.getID() + " has delivered an order for PARTY " + e.p.getID() + " to the KITCHEN area.");
    }

    // Takes food from kitchen to party
    private void handleFoodDeliveryEvent(FoodDeliveryEvent e) {
        // Update locations
        e.s.setLocation(e.p.getLocation());

        // Update states
        e.p.setDeliveryTime(time);
        eating_list.add(e.p);

        // Update entities
        server_idle_list.add(e.s);
        rating_list.add(new Rating(e.p));

        double est_time = time + e.p.getOrder().getConsumptionTime();
        event_pq.add(new FinishedEatingEvent(est_time, e.p));

        // Print if necessary
        if (verbose)
            System.out.println(Math.round(time * 100.0) / 100.0 + ": SERVER " + e.s.getID() + " has delivered food to PARTY " + e.p.getID() + " in the " + e.p.getLocation() + " area.");
    }

    private void handleFinishedEatingEvent(FinishedEatingEvent e) {
        // Update locations

        // Update states
        eating_list.remove(e.p);
        finished_eating_list.add(e.p);

        // Update entities

        // Print if necessary
        if (verbose)
            System.out.println(Math.round(time * 100.0) / 100.0 + ": PARTY " + e.p.getID() + " has finished eating.");
    }

    private void handlePaymentEvent(PaymentEvent e) {
        // Update locations

        // Update states

        dirty_table_list.add(e.p);

        // Update entities
        host_idle_list.add(e.h);

        // Print if necessary
        if (verbose)
            System.out.println(Math.round(time * 100.0) / 100.0 + ": PARTY " + e.p.getID() + " has finished paying.");

    }

    private void handleCleanTableEvent(CleanTableEvent e) {
        Area seating_area = e.p.getLocation();

        // Update locations
        e.s.setLocation(seating_area);

        // Update states
        if (seating_area == Area.DINING)
            venue.dining.removeParty(e.p);  // Clears tables from dining
        else if (seating_area == Area.BAR)
            venue.bar.removeParty(e.p);  // Clears tables from bar

        // Update entities
        double est_time = time + computeTravelTime(venue.distance(seating_area, Area.KITCHEN));
        event_pq.add(new DishesDeliveryEvent(est_time, e.p, e.s));

        // Print if necessary
        if (verbose)
            System.out.println(Math.round(time * 100.0) / 100.0 + ": SERVER " + e.s.getID() + " has cleaned the table for PARTY " + e.p.getID() + " at the " + e.p.getLocation() + " area.");
    }

    private void handleDishesDeliveryEvent(DishesDeliveryEvent e) {
        // Update locations
        e.s.setLocation(Area.KITCHEN);

        // Update states
        dishes_list.add(e.p);

        // Update entities
        server_idle_list.add(e.s);

        // Print if necessary
        if (verbose)
            System.out.println(Math.round(time * 100.0) / 100.0 + ": SERVER " + e.s.getID() + " has bought the dishes to the KITCHEN area.");
    }

    private void handleRestroomEvent(RestroomEvent e) {
        // Update locations

        // Update states

        // Update entities

    }

    private void handleCookingEvent(CookingEvent e) {
        // Update locations

        // Update states
        finished_cook_list.add(e.p);

        // Update entities
        cooks_idle_list.add(e.cook);

        if (verbose)
            System.out.println(Math.round(time * 100.0) / 100.0 + ": COOKS have finished cooking the food for PARTY " + e.p.getID() + ".");
    }

    private void handleDishwashingEvent(DishwashingEvent e) {
        // Update locations

        // Update states
        dishes_list.remove(e.p);

        // Update entities
        dishwasher_idle_list.add(e.d);

        if (verbose)
            System.out.println(Math.round(time * 100.0) / 100.0 + ": DISHWASHERS have finished the washing dishes for PARTY " + e.p.getID() + ".");
    }

    private void handlePrepareDrinkEvent(PrepareDrinkEvent e) {
        // Update locations

        // Update states
        finished_drink_list.add(e.p);

        // Update entities
        bartender_idle_list.add(e.bartender);

        if (verbose)
            System.out.println(Math.round(time * 100.0) / 100.0 + ": BARTENDERS have finished preparing the drinks for PARTY " + e.p.getID() + ".");
    }

    // Takes food from kitchen to party
    private void handleDrinkDeliveryEvent(DrinkDeliveryEvent e) {
        // Update locations
        e.s.setLocation(e.p.getLocation());

        // Update states

        // Update entities
        server_idle_list.add(e.s);

        // Print if necessary
        if (verbose)
            System.out.println(Math.round(time * 100.0) / 100.0 + ": SERVER " + e.s.getID() + " has delivered drinks to PARTY " + e.p.getID() + " in the " + e.p.getLocation() + " area.");
    }

    private double computeTravelTime(double distance) {
        return getVariation() * (distance / RATE_OF_WALKING);
    }

    private double computeCookTime(double single_cook_time) {
        return getVariation() * (single_cook_time / ((double) num_cooks));
    }

    private double computePrepareDrinkTime(double single_bartender_time) {
        return getVariation() * (single_bartender_time / ((double) num_bartenders));
    }

    private double computeCleaningTime(double num_dishes) {
        return getVariation() * (12 * num_dishes / 100.0);
    }

    private double computePaymentTime() {
        return getVariation() * 1.0;
    }

    private double computeOrderTime(double num_dishes) {
        return getVariation() * (num_dishes / 10.0);
    }

    private double computeDishwashingTime(double num_dishes) {
        return getVariation() * (num_dishes / (6.0 * (double) num_dishwashers));
    }

    // Returns a random value between 0.8 and 1.2
    private double getVariation() {
        return (0.8 + (0.4 * r.nextDouble()));
    }

    private void checkStates() {
        // Dining Wait List
        if (!host_idle_list.isEmpty()) {
            if (!dining_wait_list.isEmpty()) {
                if (venue.dining.canSeat(dining_wait_list.get(0))) {
                    Party p = dining_wait_list.get(0);
                    Host h = host_idle_list.get(0);
                    dining_wait_list.remove(p);
                    host_idle_list.remove(h);
                    double est_time = time + computeTravelTime(venue.distance(h.getLocation(), Area.ENTRANCE));
                    est_time += computeTravelTime(venue.distance(Area.ENTRANCE, Area.DINING));
                    event_pq.add(new SeatingEvent(est_time, p, h));
                }
            }
        }

        // Bar Wait List
        if (!host_idle_list.isEmpty()) {
            if (!bar_wait_list.isEmpty()) {
                if (venue.bar.canSeat(bar_wait_list.get(0))) {
                    Party p = bar_wait_list.get(0);
                    Host h = host_idle_list.get(0);
                    bar_wait_list.remove(p);
                    host_idle_list.remove(h);
                    double est_time = time + computeTravelTime(venue.distance(h.getLocation(), Area.ENTRANCE));
                    est_time += computeTravelTime(venue.distance(Area.ENTRANCE, Area.BAR));
                    event_pq.add(new SeatingEvent(est_time, p, h));
                }
            }
        }

        // Seated but not ordered
        if (!seated_list.isEmpty()) {
            if (!server_idle_list.isEmpty()) {
                Party p = seated_list.get(0);
                Server s = server_idle_list.get(0);
                seated_list.remove(p);
                server_idle_list.remove(s);
                double est_time = time + computeTravelTime(venue.distance(s.getLocation(), p.getLocation()));
                est_time += computeOrderTime(p.getOrder().size());
                event_pq.add(new TakeOrderEvent(est_time, p, s));
            }
        }

        // Food orders queued and cooks available
        if (!cooks_idle_list.isEmpty()) {
            if (!cook_list.isEmpty()) {
                Cook cook = cooks_idle_list.get(0);
                Party p = cook_list.get(0);
                cooks_idle_list.remove(cook);
                cook_list.remove(p);
                double est_time = time + computeCookTime(p.getOrder().getCookingTime());
                event_pq.add(new CookingEvent(est_time, p, cook));
            }
        }

        // Drink orders queued and bartender available
        if (!bartender_idle_list.isEmpty()) {
            if (!drink_list.isEmpty()) {
                Bartender bartender = bartender_idle_list.get(0);
                Party p = drink_list.get(0);
                bartender_idle_list.remove(bartender);
                drink_list.remove(p);
                double est_time = time + computePrepareDrinkTime(p.getOrder().getPrepareDrinkTime());
                event_pq.add(new PrepareDrinkEvent(est_time, p, bartender));
            }
        }

        // Cooking orders finished and servers available
        if (!finished_cook_list.isEmpty()) {
            if (!server_idle_list.isEmpty()) {
                Party p = finished_cook_list.get(0);
                Server s = server_idle_list.get(0);
                finished_cook_list.remove(p);
                server_idle_list.remove(s);
                double est_time = time + computeTravelTime(venue.distance(s.getLocation(), Area.KITCHEN));
                est_time += computeTravelTime(venue.distance(Area.KITCHEN, p.getLocation()));
                event_pq.add(new FoodDeliveryEvent(est_time, p, s));
            }
        }

        // Drink order finished and servers available
        if (!finished_drink_list.isEmpty()) {
            if (!server_idle_list.isEmpty()) {
                Party p = finished_drink_list.get(0);
                Server s = server_idle_list.get(0);
                finished_drink_list.remove(p);
                server_idle_list.remove(s);
                double est_time = time + computeTravelTime(venue.distance(s.getLocation(), Area.BAR));
                est_time += computeTravelTime(venue.distance(Area.BAR, p.getLocation()));
                event_pq.add(new DrinkDeliveryEvent(est_time, p, s));
            }
        }

        // If party finished eating and a host is available
        if (!finished_eating_list.isEmpty()) {
            if (!host_idle_list.isEmpty()) {
                Party p = finished_eating_list.get(0);
                Host h = host_idle_list.get(0);
                finished_eating_list.remove(p);
                host_idle_list.remove(h);
                double est_time = time + computeTravelTime(venue.distance(h.getLocation(), p.getLocation()));  // Host to Party
                est_time += computePaymentTime();  // Party paying
                est_time += computeTravelTime(venue.distance(h.getLocation(), Area.ENTRANCE));  // Host to Entrance
                est_time += computeTravelTime(venue.distance(Area.ENTRANCE, p.getLocation()));  // Host to Party for change
                event_pq.add(new PaymentEvent(est_time, p, h));
            }
        }

        // If there is a dirty table and a server available
        if (!dirty_table_list.isEmpty()) {
            if (!server_idle_list.isEmpty()) {
                Party p = dirty_table_list.get(0);
                Server s = server_idle_list.get(0);
                dirty_table_list.remove(p);
                server_idle_list.remove(s);
                double est_time = time + computeTravelTime(venue.distance(s.getLocation(), p.getLocation()));
                est_time += computeCleaningTime(p.getOrder().size());
                event_pq.add(new CleanTableEvent(est_time, p, s));
            }
        }

        // If there is an available dishwasher and dishes to be washed
        if (!dishwasher_idle_list.isEmpty()) {
            if (!dishes_list.isEmpty()) {
                Dishwasher d = dishwasher_idle_list.get(0);
                Party p = dishes_list.get(0);
                dishwasher_idle_list.remove(d);
                dishes_list.remove(p);
                double est_time = time + computeDishwashingTime(p.getOrder().size());
                event_pq.add(new DishwashingEvent(est_time, p, d));
            }
        }
    }

    private void emptyWaitLists() {
        for(Party p : dining_wait_list) {
            p.setSeatedTime(Double.MAX_VALUE);
            p.setOrderTime(Double.MAX_VALUE);
            p.setDeliveryTime(Double.MAX_VALUE);
            p.getOrder().setCost(0);
            rating_list.add(new Rating(p));
        }
        for(Party p : bar_wait_list) {
            p.setSeatedTime(Double.MAX_VALUE);
            p.setOrderTime(Double.MAX_VALUE);
            p.setDeliveryTime(Double.MAX_VALUE);
            p.getOrder().setCost(0);
            rating_list.add(new Rating(p));
        }
        dining_wait_list = new ArrayList<>();
        bar_wait_list = new ArrayList<>();
    }

    public void compileStats() {
        int total_customers_served = 0;
        double average_rating = 0.0;
        double total_earnings = 0.0;
        for(Rating r : rating_list) {
            average_rating += (r.rating * r.p.size());  // Each customer leaves the same rating in that party
            total_customers_served += r.p.size();
            total_earnings += (r.p.getOrder().getCost());
        }
        average_rating = (average_rating / (double) total_customers_served);
        average_rating = Math.round(average_rating * 100.0) / 100.0;
        total_earnings = Math.round(total_earnings * 100.0) / 100.0;

        double running_time = actual_finish_time / 60.0;

        double overtime_hours = (actual_finish_time - closing_time) / 60.0;

        double cost_per_hour = 0;
        double cooks_per_hour = Chef.salary + ((num_cooks - 1) * Cook.salary);
        double bartenders_per_hour = num_bartenders * Bartender.salary;
        double hosts_per_hour = num_hosts * Host.salary;
        double servers_per_hour = num_servers * Server.salary;
        double dishwashers_per_hour = num_dishwashers * Dishwasher.salary;
        cost_per_hour += cooks_per_hour + bartenders_per_hour + hosts_per_hour + servers_per_hour + dishwashers_per_hour;

        double overtime_cost = (1.5 * cost_per_hour * (actual_finish_time - closing_time)) / 60.0;
        overtime_cost = (Math.round(overtime_cost * 100.0) / 100.0);
        double normal_cost = (cost_per_hour * closing_time) / 60.0;
        normal_cost = (Math.round(normal_cost * 100.0) / 100.0);
        double total_cost = overtime_cost + normal_cost;
        total_cost = (Math.round(total_cost * 100.0) / 100.0);
        double net_profit = total_earnings - total_cost;
        net_profit = (Math.round(net_profit * 100.0) / 100.0);

        stats = new Stats(running_time, total_earnings, total_customers_served, average_rating, cost_per_hour, 10, normal_cost, overtime_hours, overtime_cost, total_cost, net_profit);
    }

    public void displayStats() {
        if (stats == null)
            return;

        System.out.println("Running time (hrs): " + stats.running_time);
        System.out.println("Total earnings: " + stats.total_earnings);
        System.out.println("Total customers served: " + stats.customers_served);
        System.out.println("Average rating: " + stats.average_rating);
        System.out.println("\nCost per hour: " + stats.cost_per_hour);
        System.out.println("Normal hours: " + stats.normal_hours);
        System.out.println("Normal cost: " + stats.normal_cost);
        System.out.println("Overtime hours (1.5x): " + stats.overtime_hours);
        System.out.println("Overtime cost: " + stats.overtime_cost);
        System.out.println("Total Upkeep cost: " + stats.total_upkeep_cost);
        System.out.println("Net Profit (Not including material cost): " + stats.net_profit);
    }

    private double getExp(double time, double populartiy) {
        double rate_1 = 1.0 / 4.0;  // Lowest
        double rate_2 = 1.0 / 3.0;  // Mid
        double rate_3 = 1.0 / 2.0;  // Peak

        double rate = 0.0;
        if (time < 60)
            rate = rate_1;
        else if (time < 120)
            rate = rate_2;
        else if (time < 180)
            rate = rate_3;
        else if (time < 240)
            rate = rate_2;
        else if (time < 360)
            rate = rate_1;
        else if (time < 420)
            rate = rate_2;
        else if (time < 480)
            rate = rate_3;
        else if (time < 540)
            rate = rate_2;
        else if (time < 600)
            rate = rate_1;

        rate  = rate * populartiy;

        if (rate == 0.0)
            return Double.MAX_VALUE;

        return Math.log(1-r.nextDouble())/(-rate);
    }


}
