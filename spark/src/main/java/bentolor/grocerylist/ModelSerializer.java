package bentolor.grocerylist;

import bentolor.grocerylist.model.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/** Reads and writes JSON models into/from POJO models. */
public class ModelSerializer {

    private final ObjectMapper mapper = new ObjectMapper();

    public Repository readRepository(String jsonText) {
        try {
            return mapper.readValue(jsonText, Repository.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid JSON", e);
        }
    }

    public String writeRepository(Repository repository) {
        try {
            return mapper.writeValueAsString(repository);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid JSON", e);
        }
    }


}
