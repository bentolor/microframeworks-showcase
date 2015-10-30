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

    private void save() {
        serializer.serialize(dataFile, groceryLists);
    }

    public UUID createList(GroceryList groceryList) {
        UUID newId = UUID.randomUUID();
        groceryList.setId(newId);
        groceryLists.add(groceryList);
        save();
        return newId;
    }

    public Optional<GroceryList> getList(UUID id) {
         return groceryLists.stream()
                .filter(entry -> entry != null && id.equals(entry.getId()))
                .findFirst();
    }

    public GroceryLists getLists() {
        return groceryLists;
    }

    public boolean updateList(UUID uuid, GroceryList updatedList) {
        groceryLists.replaceAll(list -> uuid.equals(list.getId()) ? updatedList : list);
        return groceryLists.contains(updatedList);
    }

    public boolean deleteList(UUID uuid) {
        return groceryLists.removeIf(list -> uuid.equals(list.getId()));
    }
}
