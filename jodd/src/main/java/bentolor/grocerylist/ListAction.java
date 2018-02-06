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
import jodd.madvoc.ScopeType;
import jodd.madvoc.meta.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@MadvocAction
public class ListAction {

    @In @Scope(ScopeType.SERVLET)
    HttpServletRequest request;

    public ListAction() {
    }

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
	public JsonResult updateList(@In("id") String id) throws IOException {
        return GroceryService.get().updateGroceryList(id, request.getReader());
	}

	@Action("/list") @POST
	public JsonResult createList() throws IOException {
        BufferedReader reader = request.getReader();
        return GroceryService.get().createGroceryList(reader);
	}

}
