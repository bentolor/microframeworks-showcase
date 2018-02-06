/*
 *    Copyright 2015 Benjamin Schmid, @bentolor
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
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
