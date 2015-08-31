package bentolor.grocerylist.model;

import lombok.Data;

/** A item to buy on a grocery list. */
@Data
public class Item {

    private int quantity;
    private Unit unit;
    private String name;

    public Item() {
    }

    public Item(int quantity, Unit unit, String name) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

}
