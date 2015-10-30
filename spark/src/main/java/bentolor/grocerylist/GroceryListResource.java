package bentolor.grocerylist;

import bentolor.grocerylist.service.GroceryService;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;

public class GroceryListResource {

    public static void main(String[] args) {
        port(8080);

        GroceryService service = new GroceryService();

        post("/list", map((req, res) -> service.createGroceryList(req.body())));
        get("/list/:id", map((req, res) -> service.getGroceryList(req.params("id"))));
        put("/list/:id", map((req, res) -> service.updateGroceryList(req.params("id"), req.body())));
        delete("/list/:id", map((req, res) -> service.deleteGroceryList(req.params("id"))));
        get("/list", map((req, res) -> service.getAllGroceryLists()));
    }

    /**
     * Wrap/Delegate Spark route handling to allow decoupling of Service Logic
     * from Spark API via {@link bentolor.grocerylist.service.ResponseCreator}
     */
    private static Route map(Processor processor) {
        return (req, res) -> processor.process(req, res).handle(req, res);
    }

    private interface Processor {
        public Route process(Request req, Response res);
    }

}