package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import model.GroceryList;
import ninja.Result;
import ninja.params.PathParam;
import ninja.utils.Message;
import persistence.Repository;

import java.util.Optional;
import java.util.UUID;

import static ninja.Results.*;

@Singleton
public class GroceryListController {

    @Inject
    private Repository repository;

    public Result index() {
        return html();      // Renders by convention: /GroceryListController/index.ftl.html
    }

    public Result getAllGroceryLists() {
        return json().render(repository.getLists());
    }

    public Result getGroceryList(@PathParam("id") String id) {
        Optional<GroceryList> match = repository.getList(UUID.fromString(id));
        if (match.isPresent())
            return json().render(match.get());
        else
            return notFound().render(new Message("Could not find a grocery list with id:" + id));
    }

    public Result deleteGroceryList(@PathParam("id") String id) {
        boolean ok = repository.deleteList(UUID.fromString(id));
        return ok ? noContent() : notFound().render(new Message("List not found"));
    }

    public Result createGroceryList(GroceryList groceryList) {
        repository.createList(groceryList);
        return ok().render(groceryList);
    }

    public Result updateGroceryList(@PathParam("id") String id, GroceryList updatedList) {
        if (repository.getList(UUID.fromString(id)).isPresent()) {
            boolean ok = repository.updateList(UUID.fromString(id), updatedList);
            return ok ? noContent() : badRequest().render(new Message("Update failed"));
        } else
            return notFound().render(new Message("Could not find a grocery list with id:" + id));
    }

}
