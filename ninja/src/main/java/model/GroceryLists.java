package model;

import lombok.Data;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A collection of grocery shopping lists.
 */
@Data
public class GroceryLists implements ModelElement, List<GroceryList> {

    @Delegate
    private final List<GroceryList> groceryLists;

    public GroceryLists() {
        groceryLists = Collections.synchronizedList(new ArrayList<GroceryList>());
    }

    public GroceryLists(GroceryList... groceryLists) {
        this.groceryLists = Collections.synchronizedList(Arrays.asList(groceryLists));
    }

}
