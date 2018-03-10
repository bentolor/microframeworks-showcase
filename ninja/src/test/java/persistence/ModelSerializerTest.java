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
import model.GroceryList;
import model.GroceryLists;
import model.Item;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.UUID;

import static model.Unit.*;
import static org.junit.Assert.*;

public class ModelSerializerTest {

    private GroceryLists groceryLists;
    private DefaultModelSerializer serializer;
    private GroceryList novemberList;
    private GroceryList decemberList;

    @Before
    public void setUp() {
        Item apples = new Item(3, pcs, "Red apples");
        Item flour = new Item(2, kg, "Flour");
        Item soyMilk = new Item(1, l, "Soy milk");

        LocalDate date1 = LocalDate.of(2015, 12, 21);
        LocalDate date2 = LocalDate.of(2015, 8, 15);
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        novemberList = new GroceryList(uuid1, date1, "My November shopping list", true,
                new Item[] { apples, flour, soyMilk });
        decemberList = new GroceryList(uuid2, date2, "December shopping List", false, null);

        groceryLists = new GroceryLists(novemberList, decemberList);
        serializer = new DefaultModelSerializer();
        serializer.mapper = new ObjectMapper();
        serializer.df = DateFormat.getDateInstance();
        serializer.configureObjectMapper();
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