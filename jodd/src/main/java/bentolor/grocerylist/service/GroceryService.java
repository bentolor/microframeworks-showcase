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
package bentolor.grocerylist.service;

import bentolor.grocerylist.JsonData;
import bentolor.grocerylist.model.GroceryList;
import bentolor.grocerylist.persistence.ModelSerializer;
import bentolor.grocerylist.persistence.Repository;

import java.io.Reader;
import java.util.Optional;
import java.util.UUID;

/**
 * A class handling, executing and answering Requests from the REST endpoints.
 */
public class GroceryService {

    private static final GroceryService INSTANCE = new GroceryService();

    private final Repository repository;
    private final ModelSerializer serializer;

    private GroceryService() {
        this.repository = new Repository();
        this.serializer = new ModelSerializer();
    }

    public static GroceryService get() {
        return INSTANCE;
    }

    public JsonData createGroceryList(Reader listJson) {
        GroceryList newList = ModelSerializer.get().deserialize(listJson, GroceryList.class);
        return new JsonData(repository.createList(newList));
    }

    public JsonData getGroceryList(String id) {
        Optional<GroceryList> match = repository.getList(UUID.fromString(id));
        if (match.isPresent())
            return new JsonData(match.get());
        else
            return JsonData.notFound("Could not find a grocery list with id:" + id);
    }

    public JsonData getAllGroceryLists() {
        return new JsonData(repository.getLists());
    }

    public JsonData updateGroceryList(String id, Reader listJson) {
        if (repository.getList(UUID.fromString(id)).isPresent()) {
            GroceryList updatedList = serializer.deserialize(listJson, GroceryList.class);
            boolean ok = repository.updateList(UUID.fromString(id), updatedList);
            return ok ? JsonData.noContent() : JsonData.badRequest("Update failed");
        } else
            return JsonData.notFound("Could not find a grocery list with id:" + id);
    }

    public JsonData deleteGroceryList(String id) {
        boolean ok = repository.deleteList(UUID.fromString(id));
        return ok ? JsonData.noContent() : JsonData.notFound("List not found");
    }
}
