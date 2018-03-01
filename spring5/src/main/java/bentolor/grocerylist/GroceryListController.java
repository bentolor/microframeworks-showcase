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

import bentolor.grocerylist.model.GroceryList;
import bentolor.grocerylist.persistence.ReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
@RestController
@RequiredArgsConstructor
public class GroceryListController {

    private final ReactiveRepository repository;

    @GetMapping(path = "/lists",
            produces = { MediaType.APPLICATION_JSON_VALUE })
    Flux<GroceryList> listStream() {
        return repository.getLists();
    }

    @GetMapping("/list/{id}")
    public Mono<GroceryList> getList(@PathVariable(value = "id") UUID id) {
        return repository.getList(id);
    }

    @PostMapping("/list")
    public Mono<UUID> createList(GroceryList newList) {
        return repository.createList(newList);
    }

    @PutMapping("/list/{id}")
    public Mono<GroceryList> createList(@PathVariable(value = "id") UUID id, GroceryList updatedList) {
        return repository.updateList(id, updatedList);
    }

    @DeleteMapping("/list/{id}")
    public Mono<UUID> deleteList(@PathVariable(value = "id") UUID id) {
        return repository.deleteList(id);
    }


}
