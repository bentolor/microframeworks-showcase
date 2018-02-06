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
package bentolor.grocerylist.controller;

import bentolor.grocerylist.model.GroceryList;
import bentolor.grocerylist.persistence.Repository;
import ro.pippo.controller.*;
import ro.pippo.controller.extractor.Body;
import ro.pippo.controller.extractor.Param;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
@Path("/list")
@Produces(Produces.JSON)
public class GroceryListController extends Controller {

    private final Repository repository;

    public GroceryListController(Repository repository) {
        this.repository = repository;
    }

    @GET
    public void lists() {
        getResponse().json(repository.getLists());
    }

    @POST
    public void createList(@Body GroceryList newGroceryList) {
        getResponse().json(repository.createList(newGroceryList));
    }

    @PUT("/{id: [\\w-]+}")
    public void updateList(@Param("id") UUID id, @Body GroceryList updatedList) {
        if (repository.updateList(id, updatedList)) {
            getResponse().ok();
        } else {
            getResponse().notFound();
        }
    }

    @GET("/{id: [\\w-]+}")
    public void getList(@Param("id") UUID id) {
        Optional<GroceryList> list = repository.getList(id);
        if (list.isPresent()) {
            getResponse().json(list.get());
        } else {
            getResponse().notFound();
        }
    }

    @DELETE("/{id: [\\w-]+}")
    public void deleteList(@Param("id") UUID id) {
        if (repository.deleteList(id)) {
            getResponse().ok();
        } else {
            getResponse().notFound();
        }
    }

}
