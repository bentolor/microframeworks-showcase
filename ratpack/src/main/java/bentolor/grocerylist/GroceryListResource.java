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

import bentolor.grocerylist.persistence.ModelSerializer;
import bentolor.grocerylist.service.GroceryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

public final class GroceryListResource {
    public static void main(String... args) throws Exception {
        ObjectMapper objectMapper = ModelSerializer.buildConfiguredObjectMapper();
        GroceryService service = new GroceryService();
        RatpackServer.start(server -> server
                .serverConfig(configBldr -> configBldr.baseDir(BaseDir.find()).development(true))
                .registryOf(r -> r.add(objectMapper))
                .handlers(chain -> chain
                        .prefix("list", nested -> {
                            nested.path(ctx -> ctx.byMethod(method -> {
                                method.post(() -> service.createGroceryList(ctx));
                                method.get(() -> service.getAllGroceryLists(ctx));
                            }));
                            nested.path(":id", ctx -> ctx.byMethod(method -> {
                                method.put(() -> service.updateGroceryList(ctx));
                                method.get(() -> service.getGroceryList(ctx));
                                method.delete(() -> service.deleteGroceryList(ctx));
                            }));
                        })
                        .files(f -> f.indexFiles("index.html"))
                )
        );
    }

}