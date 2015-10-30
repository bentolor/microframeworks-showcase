package persistence;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import model.GroceryList;
import model.GroceryLists;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

/**
 * A minimalistic, file-based persistence repository for {@link bentolor.grocerylist.model.GroceryLists}.
 */
@Singleton
public class Repository {

    private final ModelSerializer serializer;
    private final GroceryLists groceryLists;
    private final File dataFile;

    @Inject
    public Repository(@Named("datastore") File dataFile, ModelSerializer serializer) {
        this.dataFile = dataFile;
        this.serializer = serializer;
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
        save();
        return groceryLists.contains(updatedList);
    }

    public boolean deleteList(UUID uuid) {
        boolean success = groceryLists.removeIf(list -> uuid.equals(list.getId()));
        save();
        return success;
    }
}
