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

import bentolor.grocerylist.JsonResult;
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

    public JsonResult createGroceryList(Reader listJson) {
        GroceryList newList = serializer.deserialize(listJson, GroceryList.class);
        return new JsonResult(repository.createList(newList));
    }

    public JsonResult getGroceryList(String id) {
        Optional<GroceryList> match = repository.getList(UUID.fromString(id));
        if (match.isPresent())
            return new JsonResult(match.get());
        else
            return JsonResult.notFound("Could not find a grocery list with id:" + id);
    }

    public JsonResult getAllGroceryLists() {
        return new JsonResult(repository.getLists());
    }

    public JsonResult updateGroceryList(String id, Reader listJson) {
        if (repository.getList(UUID.fromString(id)).isPresent()) {
            GroceryList updatedList = serializer.deserialize(listJson, GroceryList.class);
            boolean ok = repository.updateList(UUID.fromString(id), updatedList);
            return ok ? JsonResult.noContent() : JsonResult.badRequest("Update failed");
        } else
            return JsonResult.notFound("Could not find a grocery list with id:" + id);
    }

    public JsonResult deleteGroceryList(String id) {
        boolean ok = repository.deleteList(UUID.fromString(id));
        return ok ? JsonResult.noContent() : JsonResult.notFound("List not found");
    }
}
