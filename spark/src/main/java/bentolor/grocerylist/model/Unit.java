package bentolor.grocerylist.model;

/** Unit classification for groceries. */
public enum Unit implements ModelElement {

    Pcs("pcs"),
    Kilogram("kg"),
    Liter("l");

    private final String name;

    private Unit(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
