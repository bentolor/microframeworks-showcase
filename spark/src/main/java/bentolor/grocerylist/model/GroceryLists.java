package bentolor.grocerylist.model;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/** A collection of grocery shopping lists. */
@Data
public class GroceryLists {

    private List<GroceryList> groceryLists;

    public GroceryLists() {
    }

    public GroceryLists(GroceryList... groceryLists) {
        this.groceryLists = Arrays.asList(groceryLists);
    }

}
