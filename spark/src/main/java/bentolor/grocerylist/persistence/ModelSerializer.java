package bentolor.grocerylist.persistence;

import bentolor.grocerylist.model.GroceryLists;
import bentolor.grocerylist.model.ModelElement;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Reads and writes JSON models into/from POJO models.
 */
public class ModelSerializer {

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T deserialize(String jsonText, Class<T> valueClass) {
        try {
            return mapper.readValue(jsonText, valueClass);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid JSON", e);
        }
    }

    public String serialize(ModelElement modelElement) {
        try {
            return mapper.writeValueAsString(modelElement);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid JSON", e);
        }
    }

    public GroceryLists deserialize(File sourceFile) {
        try {
            if (sourceFile.canRead())
                return mapper.readValue(sourceFile, GroceryLists.class);
            else
                return new GroceryLists();
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid JSON", e);
        }
    }

    public void serialize(File targetFile, GroceryLists groceryLists) {
        try {
            mapper.writeValue(targetFile, groceryLists);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid JSON", e);
        }
    }


}
