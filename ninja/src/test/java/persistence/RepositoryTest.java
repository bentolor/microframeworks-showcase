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
package persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import conf.Module;
import model.GroceryList;
import model.GroceryLists;
import model.Item;
import model.Unit;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;


/**
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
public class RepositoryTest {

    private static final UUID UUID_1 = UUID.fromString("68798a5c-be46-41b8-985a-1b6f32fefd4c");
    private static final UUID UUID_2 = UUID.fromString("5e9d65d7-8377-4013-a73f-7389fdcb8bcd");
    private static final UUID UUID_3 = UUID.fromString("723a1872-9806-40d6-8540-cedd95209fbd");
    private static final Item ITEM_APPLES = new Item(3, Unit.pcs, "Apples");
    private static final Item ITEM_SOY_MILK = new Item(1, Unit.l, "soy milk");
    private Repository defaultRepository;
    private Repository emptyRepository;
    private GroceryList groceryList;

    @Before
    public void setUp() throws IOException {
        DefaultModelSerializer ms = new DefaultModelSerializer();
        ms.df = new Module.JsonDateFormatProvider().get();
        ms.mapper = new ObjectMapper();
        ms.configureObjectMapper();
        defaultRepository = new Repository(new File("grocerylists.json"), ms);
        emptyRepository = new Repository(null, ms);
        Item[] items = {ITEM_APPLES, ITEM_SOY_MILK};
        groceryList = new GroceryList(null, LocalDate.of(2018, 1, 1), "Shopping spree", false, items);
    }

    @Test
    public void getList() {
        assertTrue("#1", defaultRepository.getList(UUID_1).isPresent());
        assertTrue("#2", defaultRepository.getList(UUID_2).isPresent());
        assertTrue("#3", defaultRepository.getList(UUID_3).isPresent());
        assertEquals(2, defaultRepository.getList(UUID_2).get().getShoppingItems().length);
    }

    @Test
    public void getLists() {
        GroceryLists defList = defaultRepository.getLists();
        assertEquals("Broken default data?", 3, defList.size());
        assertTrue(defList.stream().map(GroceryList::getId).anyMatch(uuid -> Objects.equals(UUID_2, uuid)));

        GroceryLists emptyList = emptyRepository.getLists();
        assertEquals("Empty set not empty?", 0, emptyList.size());

    }

    @Test
    public void createList() {
        assertNull(groceryList.getId());
        emptyRepository.createList(groceryList);
        assertNotNull(groceryList.getId());
        assertTrue(emptyRepository.getList(groceryList.getId()).isPresent());
    }

    @Test
    public void updateList() {
        emptyRepository.createList(groceryList);
        groceryList.setSettled(true);
        groceryList.setShoppingItems(new Item[] { ITEM_SOY_MILK });
        assertTrue(emptyRepository.updateList(groceryList.getId(), groceryList));

        Optional<GroceryList> updatedList = emptyRepository.getList(groceryList.getId());
        assertTrue(updatedList.isPresent());
        assertEquals(1, updatedList.get().getShoppingItems().length);
        assertEquals("soy milk", updatedList.get().getShoppingItems()[0].getName());
    }

    @Test
    public void updateListWithMissingUUID() {
        emptyRepository.createList(groceryList);
        UUID uuid = groceryList.getId();
        groceryList.setSettled(true);
        groceryList.setId(null);
        assertTrue(emptyRepository.updateList(uuid, groceryList));

        Optional<GroceryList> updatedList = emptyRepository.getList(uuid);
        assertTrue(updatedList.isPresent());
        assertTrue(updatedList.get().isSettled());
    }

    @Test
    public void deleteList() {
        List<GroceryList> defaultLists = defaultRepository.getLists().getGroceryLists();
        defaultLists.forEach(sl -> emptyRepository.createList(sl));
        assertTrue(emptyRepository.deleteList(defaultLists.get(1).getId()));
        assertFalse(emptyRepository.deleteList(UUID.randomUUID()));
        assertEquals(2, emptyRepository.getLists().size());
    }
}