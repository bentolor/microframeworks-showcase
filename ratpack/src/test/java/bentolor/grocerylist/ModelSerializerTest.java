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
import bentolor.grocerylist.model.GroceryLists;
import bentolor.grocerylist.model.Item;
import bentolor.grocerylist.persistence.ModelSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.UUID;

import static bentolor.grocerylist.model.Unit.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModelSerializerTest {

    private GroceryLists groceryLists;
    private ModelSerializer serializer;
    private GroceryList novemberList;
    private GroceryList decemberList;

    @BeforeEach
    public void setUp() {
        Item apples = new Item(3, pcs, "Red apples");
        Item flour = new Item(2, kg, "Flour");
        Item soyMilk = new Item(1, l, "Soy milk");

        LocalDate date1 = LocalDate.of(2015, 12, 21);
        LocalDate date2 = LocalDate.of(2015, 8, 15);
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        novemberList = new GroceryList(uuid1, date1, "My November shopping list", true, apples, flour, soyMilk);
        decemberList = new GroceryList(uuid2, date2, "December shopping List", false);

        groceryLists = new GroceryLists(novemberList, decemberList);
        serializer = new ModelSerializer();
    }

    @Test
    public void testReadWriteCycle() {
        String jsonText = serializer.serialize(groceryLists);
        assertNotNull(jsonText);
        GroceryLists deserialized = serializer.deserialize(jsonText, GroceryLists.class);
        System.out.println(jsonText);
        assertEquals(groceryLists, deserialized);
    }

    @Test
    public void testDateReadWrite() {
        String jsonText = serializer.serialize(novemberList);
        assertTrue(jsonText.contains("2015-12-21"));
        GroceryList deserialized = serializer.deserialize(jsonText, GroceryList.class);
        assertEquals(novemberList.getDate(), deserialized.getDate());
    }

    @Test
    public void testFileReadWriteCycle() throws Exception {
        File tmpFile = File.createTempFile("ModelSerializerTest", ".json");
        serializer.serialize(tmpFile, groceryLists);
        GroceryLists deserialized = serializer.deserialize(tmpFile);
        assertEquals(groceryLists, deserialized);
    }
}