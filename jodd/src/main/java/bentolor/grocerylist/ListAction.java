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

import jodd.madvoc.ScopeType;
import jodd.madvoc.meta.*;

import javax.servlet.http.HttpServletRequest;
import java.io.StringReader;

import static jodd.madvoc.ScopeType.SERVLET;

@MadvocAction
public class ListAction {

    @In @Scope(ScopeType.SERVLET)
    HttpServletRequest request;

    @Action("/list") @GET
    public JsonResult listAllLists() {
        return GroceryService.get().getAllGroceryLists();
    }

    @Action("/list/{id}") @GET
    public JsonResult getList(@In("id") String id) {
        return GroceryService.get().getGroceryList(id);
    }

    @Action("/list/{id}") @DELETE
    public JsonResult deleteList(@In("id") String id) {
        return GroceryService.get().deleteGroceryList(id);
    }

    @Action("/list/{id}") @PUT
    public JsonResult updateList(@In("id") String id, @In @Scope(SERVLET) String requestBody) {
        return GroceryService.get().updateGroceryList(id, new StringReader(requestBody));
    }

    @Action("/list") @POST
    public JsonResult createList(@In @Scope(SERVLET) String requestBody) {
        return GroceryService.get().createGroceryList(new StringReader(requestBody));
    }

}
