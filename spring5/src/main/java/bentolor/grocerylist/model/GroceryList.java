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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/** A shopping list for groceries. */
@Data
public class GroceryList implements ModelElement {

    private UUID id;
    private List<Item> shoppingItems;
    private String comment;
    private LocalDate date;
    private boolean settled;

    public GroceryList() {
    }

    public GroceryList(UUID id, LocalDate date, String comment, boolean settled, Item... shoppingItems) {
        this.id = id;
        this.shoppingItems = Arrays.asList(shoppingItems);
        this.comment = comment;
        this.date = date;
        this.settled = settled;
    }

}
