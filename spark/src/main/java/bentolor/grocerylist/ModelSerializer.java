package bentolor.grocerylist;

import bentolor.grocerylist.model.GroceryLists;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/** Reads and writes JSON models into/from POJO models. */
public class ModelSerializer {

    private final ObjectMapper mapper = new ObjectMapper();

    public GroceryLists readRepository(String jsonText) {
        try {
            return mapper.readValue(jsonText, GroceryLists.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid JSON", e);
        }
    }

    public String writeRepository(GroceryLists groceryLists) {
        try {
            return mapper.writeValueAsString(groceryLists);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid JSON", e);
        }
    }


}
