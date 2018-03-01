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
package bentolor.grocerylist.persistence;

import bentolor.grocerylist.model.GroceryList;
import bentolor.grocerylist.model.GroceryLists;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.UUID;

/**
 * A minimalistic, file-based persistence repository for {@link bentolor.grocerylist.model.GroceryLists}.
 */
@Component
public class DefaultReactiveRepository implements ReactiveRepository {

    private final ModelSerializer serializer;
    private final GroceryLists groceryLists;
    private final File dataFile;

    public DefaultReactiveRepository(/*@Named("datastore") File dataFile,*/ ModelSerializer serializer) {
        this.dataFile = new File("grocerylists.json"); //dataFile;
        this.serializer = serializer;
        this.groceryLists = serializer.deserialize(dataFile);
    }

    private void save() {
        serializer.serialize(dataFile, groceryLists);
    }

    public Mono<UUID> createList(GroceryList groceryList) {
        UUID newId = UUID.randomUUID();
        groceryList.setId(newId);
        groceryLists.add(groceryList);
        save();
        return Mono.just(newId);
    }

    public Mono<GroceryList> getList(UUID id) {
        return Mono.justOrEmpty(
                groceryLists.stream()
                        .filter(entry -> entry != null && id.equals(entry.getId()))
                        .findFirst());
    }

    public Flux<GroceryList> getLists() {
        return Flux.fromIterable(groceryLists);
    }

    public Mono<GroceryList> updateList(UUID uuid, GroceryList updatedList) {
        updatedList.setId(uuid);
        groceryLists.replaceAll(list -> uuid.equals(list.getId()) ? updatedList : list);
        save();
        return groceryLists.contains(updatedList) ? Mono.just(updatedList) : Mono.empty();
    }

    public Mono<UUID> deleteList(UUID uuid) {
        boolean success = groceryLists.removeIf(list -> uuid.equals(list.getId()));
        save();
        return success ? Mono.just(uuid) : Mono.empty();
    }
}
