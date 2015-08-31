package bentolor.grocerylist.model;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/** A collection of grocery shopping lists. */
@Data
public class Repository {

    private List<GroceryList> groceryLists;

    public Repository() {
    }

    public Repository(GroceryList... groceryLists) {
        this.groceryLists = Arrays.asList(groceryLists);
    }

}
