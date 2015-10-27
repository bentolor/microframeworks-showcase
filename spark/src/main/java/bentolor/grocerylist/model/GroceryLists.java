package bentolor.grocerylist.model;

import lombok.Data;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A collection of grocery shopping lists.
 */
@Data
public class GroceryLists implements ModelElement, List<GroceryList> {

    @Delegate
    private final List<GroceryList> groceryLists;

    public GroceryLists() {
        groceryLists = new ArrayList<GroceryList>();
    }

    public GroceryLists(GroceryList... groceryLists) {
        this.groceryLists = Arrays.asList(groceryLists);
    }

}
