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
import bentolor.grocerylist.persistence.Repository;
import ratpack.handling.Context;
import ratpack.http.Response;

import java.util.Optional;
import java.util.UUID;

import static ratpack.jackson.Jackson.json;


/**
 * A class handling, executing and answering Requests from the REST endpoints.
 */
public class GroceryService {

    private final Repository repository;

    public GroceryService() {
        this.repository = new Repository();
    }

    public void createGroceryList(Context ctx) {
        ctx.parse(GroceryList.class).then(newList -> {
            ctx.render(json(repository.createList(newList)));
        });
    }

    public void getGroceryList(Context ctx) {
        String id = ctx.getPathTokens().get("id");
        UUID uuid = UUID.fromString(id);
        Optional<GroceryList> match = repository.getList(uuid);
        if (match.isPresent())
            ctx.render(json(match.get()));
        else
            ctx.getResponse().status(404).send("Could not find a grocery list with id:" + id);
    }

    public void getAllGroceryLists(Context ctx) {
        ctx.render(json(repository.getLists()));
    }

    public void updateGroceryList(Context ctx) {
        String id = ctx.getPathTokens().get("id");
        UUID uuid = UUID.fromString(id);
        if (repository.getList(UUID.fromString(id)).isPresent()) {
            ctx.parse(GroceryList.class).then(updatedList -> {
                boolean ok = repository.updateList(uuid, updatedList);
                Response res = ctx.getResponse();
                if (ok) res.status(204).send();
                else res.status(400).send("Update failed");
                ctx.render(json(repository.createList(updatedList)));
            });

        } else
            ctx.getResponse().status(404).send("Could not find a grocery list with id:" + id);
    }

    public void deleteGroceryList(Context ctx) {
        String id = ctx.getPathTokens().get("id");
        boolean ok = repository.deleteList(UUID.fromString(id));
        Response res = ctx.getResponse();
        if (ok) res.status(204).send();
        else res.status(404).send("List not found");
    }
}
