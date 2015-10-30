package bentolor.grocerylist.model;

/** Unit classification for groceries. */
public enum Unit implements ModelElement {

    pcs("pieces"),
    kg("kilogram"),
    g("gram"),
    l("litre");

    private final String name;

    private Unit(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
