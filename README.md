# Restaurant DES

## Summary

This was my final project for CECS 552: Modeling and Simulations with Dr. Ebert as CSULB.

The goal of this project is to generate a reasonable accurate assessment of how a (hypothetical) restaurant will perform each day. Many assumptions are made in the simulated restaurant, such as the layout, employee salaries, customer arrival times, menu items, individual event time variance, average walking speeds, and satisfaction ratings based on how long a customer has to wait to be seated and receive their order. There are a total of 13 events, 6 entities, and many more entity states. Further details can be found within the PDF.

Unfortunately, the simulation is limited to being entirely text based. Had there been more time, it would have been cool to provide a "Sims" experience with a visual representation of events. Additionally, it would have also been interesting to have attached an optimization routine onto the simulations to find configurations that would generate optimal profit margins or approval ratings.

## Prerequisites
* Java 9.0

## Usage
The console:

![alt text](../media/media/console.PNG?raw=true)

### 1. Simulate
You will be prompted to input some parameters:

![alt text](../media/media/1-1.PNG?raw=true)

* **Restaurant size:** s (small), m (medium - 2x small), l (large - 4x small)
* **Cooks:** >= 1 (Recieves orders and prepares food)
* **Bartenders:** >= 1 (Prepares drinks)
* **Hosts:** >= 1 (Receives customers and guides them to their table, delivers food and drinks to tables when idle)
* **Servers:** >= 1 (Delivers food and drinks to tables and cleans empty tables)
* **Dishwashers:** >= 1 (Washes dishes)
* **Popularity:** 0.0 - Double.MAX_VALUE (Affects the number of customers that show up, 1.0 by default)

After inputting the details, a single days worth of simulation will be run with all events to the console:

![alt text](../media/media/1-2.PNG?raw=true)

Time stamps are included on the left margin. Entity IDs and events are listed after.

Upon finishing, relevant stats are presented:

![alt text](../media/media/1-3.PNG?raw=true)

In this case, the restaurant reviews were negatively affected by too many customers, too small of a venue, and not enough staff to manage their customers.

### 2. Simulate N times
N days of the simulation are run and relevant statistics are returned. For the same input parameters as before, we get:

![alt text](../media/media/2.PNG?raw=true)

### 3. Test N parties
This option is more for debugging and the number of customer arrivals can be directly set for a single days worth of simulation. This allows me to easier track the proper sequencing of discrete events.

![alt text](../media/media/3-1.PNG?raw=true)

For the same restaurant with just 5 groups of customers:

![alt text](../media/media/3-2.PNG?raw=true)

To no surprise, having just 5 customers in a day results in low earnings and a negative net profit!
