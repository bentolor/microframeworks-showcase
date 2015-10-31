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
package bentolor.grocerylist.model;

import lombok.Data;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A collection of grocery shopping lists.
 */
@Data
public class GroceryLists implements ModelElement, List<GroceryList> {

    @Delegate
    private final List<GroceryList> groceryLists;

    public GroceryLists() {
        groceryLists = Collections.synchronizedList(new ArrayList<GroceryList>());
    }

    public GroceryLists(GroceryList... groceryLists) {
        this.groceryLists = Collections.synchronizedList(Arrays.asList(groceryLists));
    }

}
