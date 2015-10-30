package bentolor.grocerylist;

import bentolor.grocerylist.model.GroceryList;
import bentolor.grocerylist.model.GroceryLists;
import bentolor.grocerylist.model.Item;
import bentolor.grocerylist.persistence.ModelSerializer;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import static bentolor.grocerylist.model.Unit.*;
import static org.junit.Assert.*;

public class ModelSerializerTest {

    private GroceryLists groceryLists;
    private ModelSerializer serializer;
    private GroceryList novemberList;
    private GroceryList decemberList;

    @Before
    public void setUp() throws Exception {
        Item apples = new Item(3, pcs, "Red apples");
        Item flour = new Item(2, kg, "Flour");
        Item soyMilk = new Item(1, l, "Soy milk");

        soyMilk.getName();

        Date date1 = new Date(1450652400000l);
        Date date2 = new Date(1513724400000l);
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        novemberList = new GroceryList(uuid1, date1, "My November shopping list", true, apples, flour, soyMilk);
        decemberList = new GroceryList(uuid2, date2, "December shopping List", false);

        groceryLists = new GroceryLists(novemberList, decemberList);
        serializer = new ModelSerializer();
    }

    @Test
    public void testReadWriteCycle() throws Exception {
        String jsonText = serializer.serialize(groceryLists);
        assertNotNull(jsonText);
        GroceryLists deserialized = serializer.deserialize(jsonText, GroceryLists.class);
        System.out.println(jsonText);
        assertEquals(groceryLists, deserialized);
    }

    @Test
    public void testDateReadWrite() throws Exception {
        String jsonText = serializer.serialize(novemberList);
        assertTrue(jsonText.contains("2015-12-21"));
        GroceryList deserialized = serializer.deserialize(jsonText, GroceryList.class);
        assertEquals(novemberList.getDate(), deserialized.getDate());
    }

    @Test
    public void testFileReadWriteCycle() throws Exception {
        File tmpFile = File.createTempFile("ModelSerializerTest",".json");
        serializer.serialize(tmpFile, groceryLists);
        GroceryLists deserialized = serializer.deserialize(tmpFile);
        assertEquals(groceryLists, deserialized);
    }
}