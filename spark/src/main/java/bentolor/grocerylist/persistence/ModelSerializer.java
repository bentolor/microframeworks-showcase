package bentolor.grocerylist.persistence;

import bentolor.grocerylist.model.GroceryLists;
import bentolor.grocerylist.model.ModelElement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Reads and writes JSON models into/from POJO models.
 */
public class ModelSerializer {

    private final ObjectMapper mapper;

    public ModelSerializer() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        mapper = new ObjectMapper();
        mapper.setDateFormat(df);
        mapper.setTimeZone(TimeZone.getDefault());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

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
