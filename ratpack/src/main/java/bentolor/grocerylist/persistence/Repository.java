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
package bentolor.grocerylist.persistence;

import bentolor.grocerylist.model.GroceryList;
import bentolor.grocerylist.model.GroceryLists;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

/**
 * A minimalistic, file-based persistence repository for {@link GroceryLists}.
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

    public GroceryList createList(GroceryList groceryList) {
        groceryList.setId(UUID.randomUUID());
        groceryLists.add(groceryList);
        save();
        return groceryList;
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
