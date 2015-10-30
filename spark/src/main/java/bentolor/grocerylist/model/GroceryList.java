package bentolor.grocerylist.model;

import lombok.Data;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/** A shopping list for groceries. */
@Data
public class GroceryList implements ModelElement {

    private UUID id;
    private List<Item> shoppingItems;
    private String comment;
    private Date date;
    private boolean settled;

    public GroceryList() {
    }

    public GroceryList(UUID id, Date date, String comment, boolean settled, Item... shoppingItems) {
        this.id = id;
        this.shoppingItems = Arrays.asList(shoppingItems);
        this.comment = comment;
        this.date = date;
        this.settled = settled;
    }

}
