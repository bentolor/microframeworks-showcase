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
package bentolor.grocerylist;

import bentolor.grocerylist.service.GroceryService;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;

public class GroceryListResource {

    public static void main(String[] args) {
        port(8080);
        staticFileLocation("/gui");
        //enableCORS("*", "POST,GET,PUT,DELETE", "*");

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

    /** Configure support for Cross-origin Ajax requests in Spark. */
    private static void enableCORS(final String origin, final String methods, final String headers) {
        before(new Filter() {
            @Override
            public void handle(Request request, Response response) {
                response.header("Access-Control-Allow-Origin", origin);
                response.header("Access-Control-Request-Method", methods);
                response.header("Access-Control-Allow-Headers", headers);
            }
        });
    }


}