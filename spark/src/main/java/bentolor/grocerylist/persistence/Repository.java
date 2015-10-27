package bentolor.grocerylist.persistence;

import bentolor.grocerylist.model.GroceryList;
import bentolor.grocerylist.model.GroceryLists;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

/**
 * A minimalistic, file-based persistence repository for {@link bentolor.grocerylist.model.GroceryLists}.
 */
public class Repository {

    private final File dataFile;
    private final GroceryLists groceryLists;
    private final ModelSerializer serializer;

    public Repository() {
        this.dataFile = new File("grocerylists.json");
        this.serializer = new ModelSerializer();
        this.groceryLists = serializer.deserialize(dataFile);
    }

    public GroceryLists getGroceryLists() {
        return groceryLists;
    }

    /**
     * Adds / creates a new {@link bentolor.grocerylist.model.GroceryList}
     * @param groceryList JSON representation of a grocery list
     * @return the generated ID.
     */
    public UUID createList(GroceryList groceryList) {
        UUID newId = UUID.randomUUID();
        groceryList.setId(newId);
        groceryLists.add(groceryList);
        save();
        return newId;
    }

    /**
     * Adds / creates a new {@link bentolor.grocerylist.model.GroceryList}
     * @param body JSON representation of a grocery list
     * @return the generated ID.
     */
    public Optional<GroceryList> getList(UUID id) {
         return groceryLists.stream()
                .filter(entry -> entry != null && id.equals(entry.getId()))
                .findFirst();
    }


    /**
     * Save the current content of the {@link #groceryLists} to a persistent file.
     */
    private void save() {
        serializer.serialize(dataFile, groceryLists);
    }


}
