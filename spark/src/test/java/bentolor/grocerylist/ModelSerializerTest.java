package bentolor.grocerylist;

import bentolor.grocerylist.model.GroceryList;
import bentolor.grocerylist.model.Item;
import bentolor.grocerylist.model.Repository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.UUID;

import static bentolor.grocerylist.model.Unit.Kilogram;
import static bentolor.grocerylist.model.Unit.Liter;
import static bentolor.grocerylist.model.Unit.Pcs;

public class ModelSerializerTest {

    private Repository repository;
    private ModelSerializer serializer;

    @Before
    public void setUp() throws Exception {
        Item apples = new Item(3, Pcs, "Red apples");
        Item flour = new Item(2, Kilogram, "Flour");
        Item soyMilk = new Item(1, Liter, "Soy milk");

        soyMilk.getName();

        LocalDate date1 = LocalDate.of(2015, 11, 23);
        LocalDate date2 = LocalDate.of(2015, 12, 10);
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        GroceryList novemberList = new GroceryList(uuid1, date1, "My November shopping list", true, apples, flour, soyMilk);
        GroceryList decemberList = new GroceryList(uuid2, date2, "December shopping List", false);

        repository = new Repository(novemberList, decemberList);
        serializer = new ModelSerializer();
    }

    @Test
    public void testReadWriteCycle() throws Exception {
        String jsonText = serializer.writeRepository(repository);
        Assert.assertNotNull(jsonText);
        Repository deserialized = serializer.readRepository(jsonText);
        System.out.println(jsonText);
        Assert.assertEquals(repository, deserialized);
    }
}