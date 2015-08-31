package bentolor.grocerylist.model;

import lombok.Data;

import java.time.chrono.ChronoLocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/** A shopping list for groceries. */
@Data
public class GroceryList {

    private UUID id;
    private List<Item> shoppingItems;
    private String comment;
    private long date;
    private boolean settled;

    public GroceryList() {
    }

    public GroceryList(UUID id, ChronoLocalDate date, String comment, boolean settled, Item... shoppingItems) {
        this.id = id;
        this.shoppingItems = Arrays.asList(shoppingItems);
        this.comment = comment;
        this.date = date.toEpochDay();
        this.settled = settled;
    }

}
