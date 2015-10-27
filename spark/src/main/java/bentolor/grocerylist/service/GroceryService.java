package bentolor.grocerylist.service;

import bentolor.grocerylist.model.GroceryList;
import bentolor.grocerylist.persistence.ModelSerializer;
import bentolor.grocerylist.persistence.Repository;
import spark.Route;

import java.util.Optional;
import java.util.UUID;

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
        return ResponseCreator.ok("{ id: '" + newId.toString() + "'}");
    }

    public Route getGroceryList(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<GroceryList> match = repository.getList(uuid);
        if (match.isPresent())
            return ResponseCreator.ok(serializer.serialize(match.get()));
        else
            return ResponseCreator.notFound("GroceryList with id:" + id);
    }


}
