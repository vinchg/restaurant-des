package restaurantdes.entities;

/**
 * Created by Vince on 12/10/2017.
 */
public enum MenuItem {
    // Appetizers(index, price, cook_time_in_minutes, consumption_time_in_minutes)
    CHICKENWINGS(0, 3.99, 1.0, 5.0),
    EGGROLLS(1, 1.99, 0.3, 3.0),
    GARLICBREAD(2, 1.99, 0.5, 3.0),
    NACHOS(3, 1.99, 0.2, 5.0),
    ONIONRINGS(4, 2.99, 1.0, 5.0),
    SALAD(5, 1.99, 0.5, 5.0),
    FRIES(6, 1.99, 0.3, 3.0),


    // Entrees
    SANDWICH(7, 7.99, 2.0, 10.0),
    PIZZA(8, 9.99, 8.0, 15.0),
    BURGER(9, 5.99, 2.0, 5.0),
    STEAK(10, 12.99, 8.0, 10.0),
    RIBS(11, 14.99, 10.0, 10.0),
    TACOS(12, 6.99, 3.0, 5.0),


    // Drinks
    WATER(13, 0.0, 0.0, 1.0),
    TEA(14, 1.99, 0.2, 2.0),
    SODA(15, 1.99, 0.2, 2.0),
    JUICE(16, 2.99, 0.3, 1.0),
    MILK(17, 1.99, 0.2, 1.0),
    COFFEE(18, 1.99, 0.2, 1.0),
    BEER(19, 4.99, 0.2, 4.0),
    WINE(20, 9.99, 0.5, 4.0),


    // Dessert
    CHOCCAKE(21, 1.99, 0.2, 1.0),
    CHEESECAKE(22, 3.99, 0.2, 1.0),
    BROWNIE(23, 0.99, 0.2, 0.5),
    ICECREAM(24, 1.99, 0.4, 1.0),
    COOKIE(25, 0.99, 0.1, 0.5);

    private int index;
    private double price;
    private double prepare_time;
    private double consumption_time;

    MenuItem(int input_index, double input_price, double input_prepare_time, double input_consumption_time) {
        index = input_index;
        price = input_price;
        prepare_time = input_prepare_time;
        consumption_time = input_consumption_time;
    }

    public int getIndex() {
        return index;
    }

    public double getPrice() {
        return price;
    }

    public double getPrepareTime() {
        return prepare_time;
    }

    public double getConsumption_time() {
        return consumption_time;
    }
}
