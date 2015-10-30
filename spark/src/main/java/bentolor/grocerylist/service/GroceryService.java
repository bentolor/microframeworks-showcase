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

import bentolor.grocerylist.model.GroceryList;
import bentolor.grocerylist.persistence.ModelSerializer;
import bentolor.grocerylist.persistence.Repository;
import spark.Route;

import java.util.Optional;
import java.util.UUID;

import static bentolor.grocerylist.service.ResponseCreator.*;

/**
 * A class handling, executing and answering Requests from the REST endpoints.
 */
public class GroceryService {

    private final Repository repository;
    private final ModelSerializer serializer;


    public GroceryService() {
        this.repository = new Repository();
        this.serializer = new ModelSerializer();
    }

    public Route createGroceryList(String listJson) {
        GroceryList groceryList = serializer.deserialize(listJson, GroceryList.class);
        UUID newId = repository.createList(groceryList);
        return ok(serializer.serialize(groceryList));
    }

    public Route getGroceryList(String id) {
        Optional<GroceryList> match = repository.getList(UUID.fromString(id));
        if (match.isPresent())
            return ok(serializer.serialize(match.get()));
        else
            return notFound("Could not find a grocery list with id:" + id);
    }

    public Route getAllGroceryLists() {
        return ok(serializer.serialize(repository.getLists()));
    }

    public Route updateGroceryList(String id, String listJson) {
        if (repository.getList(UUID.fromString(id)).isPresent()) {
            GroceryList updatedList = serializer.deserialize(listJson, GroceryList.class);
            boolean ok = repository.updateList(UUID.fromString(id), updatedList);
            return ok ? noContent() : badRequest("Update failed");
        } else
            return notFound("Could not find a grocery list with id:" + id);
    }

    public Route deleteGroceryList(String id) {
        boolean ok = repository.deleteList(UUID.fromString(id));
        return ok ? noContent() : notFound("List not found");
    }
}
