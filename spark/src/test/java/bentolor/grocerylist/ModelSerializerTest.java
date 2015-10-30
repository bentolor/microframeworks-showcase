package bentolor.grocerylist;

import bentolor.grocerylist.model.GroceryList;
import bentolor.grocerylist.model.GroceryLists;
import bentolor.grocerylist.model.Item;
import bentolor.grocerylist.persistence.ModelSerializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.UUID;

import static bentolor.grocerylist.model.Unit.kg;
import static bentolor.grocerylist.model.Unit.l;
import static bentolor.grocerylist.model.Unit.pcs;

public class ModelSerializerTest {

    private GroceryLists groceryLists;
    private ModelSerializer serializer;

    @Before
    public void setUp() throws Exception {
        Item apples = new Item(3, pcs, "Red apples");
        Item flour = new Item(2, kg, "Flour");
        Item soyMilk = new Item(1, l, "Soy milk");

        soyMilk.getName();

        LocalDate date1 = LocalDate.of(2015, 11, 23);
        LocalDate date2 = LocalDate.of(2015, 12, 10);
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        GroceryList novemberList = new GroceryList(uuid1, date1, "My November shopping list", true, apples, flour, soyMilk);
        GroceryList decemberList = new GroceryList(uuid2, date2, "December shopping List", false);

        groceryLists = new GroceryLists(novemberList, decemberList);
        serializer = new ModelSerializer();
    }

    @Test
    public void testReadWriteCycle() throws Exception {
        String jsonText = serializer.serialize(groceryLists);
        Assert.assertNotNull(jsonText);
        GroceryLists deserialized = serializer.deserialize(jsonText, GroceryLists.class);
        System.out.println(jsonText);
        Assert.assertEquals(groceryLists, deserialized);
    }

    @Test
    public void testFileReadWriteCycle() throws Exception {
        File tmpFile = File.createTempFile("ModelSerializerTest",".json");
        serializer.serialize(tmpFile, groceryLists);
        GroceryLists deserialized = serializer.deserialize(tmpFile);
        Assert.assertEquals(groceryLists, deserialized);
    }
}