package bentolor.grocerylist;

import bentolor.grocerylist.service.GroceryService;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;

public class GroceryListResource {

    public static void main(String[] args) {
        port(8080);

        final GroceryService service = new GroceryService();

        post("/list", map((req, res) -> service.createGroceryList(req.body())));
        get("/list/:id", map((req, res) -> service.getGroceryList(req.params("id"))));
    }

    private static Route map(Processor processor) {
        return (req, res) -> processor.process(req, res).handle(req,res);
    }

    private interface Processor {
        public Route process(Request req, Response res);
    }

}