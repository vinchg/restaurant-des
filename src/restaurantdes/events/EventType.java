package restaurantdes.events;

/**
 * Created by Vince on 12/10/2017.
 */
public enum EventType {
    PartyArrival(0),
    Seating(1),
    TakeOrder(2),
    OrderDelivery(3),
    FoodDelivery(4),
    FinishedEating(5),
    Payment(6),
    CleanTable(7),
    DishesDelivery(8),
    Restroom(9),
    Cooking(10),
    Dishwashing(11),
    PrepareDrink(12),
    DrinkDelivery(13);

    private int index;

    EventType(int input_index) {
        index = input_index;
    }

    public int getIndex() {
        return index;
    }
}
