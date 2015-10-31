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
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.In;
import jodd.madvoc.meta.MadvocAction;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@MadvocAction
public class ListAction {

    @In(scope = ScopeType.SERVLET)
    HttpServletRequest request;

    public ListAction() {
    }

    @Action(value = "/list", method = Action.GET)
	public JsonData listAllLists() {
        return GroceryService.get().getAllGroceryLists();
	}

    @Action(value = "/list/${id}", method = Action.GET)
	public JsonData getList(@In("id") String id) {
        return GroceryService.get().getGroceryList(id);
	}

    @Action(value = "/list/${id}", method = Action.DELETE)
	public JsonData deleteList(@In("id") String id) {
        return GroceryService.get().deleteGroceryList(id);
	}

    @Action(value = "/list/${id}", method = Action.PUT)
	public JsonData updateList(@In("id") String id) throws IOException {
        return GroceryService.get().updateGroceryList(id, request.getReader());
	}

	@Action(value = "/list", method = Action.POST)
	public JsonData createList() throws IOException {
        BufferedReader reader = request.getReader();
        return GroceryService.get().createGroceryList(reader);
	}

}
