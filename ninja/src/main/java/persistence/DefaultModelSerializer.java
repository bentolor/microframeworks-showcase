package persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import model.GroceryLists;
import ninja.lifecycle.Start;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.TimeZone;

/**
 * Reads and writes JSON models into/from POJO models.
 */
@Singleton
public class DefaultModelSerializer implements ModelSerializer {

    @Inject
    private ObjectMapper mapper;
    @Inject
    @Named("json-dateformat")
    private DateFormat df;

    @Start(order = 90)
    public void configureObjectMapper() {
        mapper.setDateFormat(df);
        mapper.setTimeZone(TimeZone.getDefault());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
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

    @Override
    public void serialize(File targetFile, GroceryLists groceryLists) {
        try {
            mapper.writeValue(targetFile, groceryLists);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid JSON", e);
        }
    }


}
