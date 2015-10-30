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
package conf;

import controllers.GroceryListController;
import controllers.HelloWorldController;
import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;

public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {
        // Hello world example with I18N
        router.GET().route("/hello").with(HelloWorldController.class, "helloWorld");

        // Grocery List application routes
        router.GET().route("/").with(GroceryListController.class, "index");
        router.GET().route("/list").with(GroceryListController.class, "getAllGroceryLists");
        router.GET().route("/list/{id: \\w+-\\w+-\\w+-\\w+-\\w+}").with(GroceryListController.class, "getGroceryList");
        router.DELETE().route("/list/{id}").with(GroceryListController.class, "deleteGroceryList");
        router.POST().route("/list").with(GroceryListController.class, "createGroceryList");
        router.PUT().route("/list/{id}").with(GroceryListController.class, "updateGroceryList");

        // Assets (pictures / javascript)
        router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");
    }

}
