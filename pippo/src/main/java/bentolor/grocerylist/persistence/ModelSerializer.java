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
package bentolor.grocerylist.persistence;

import bentolor.grocerylist.model.GroceryLists;
import bentolor.grocerylist.model.ModelElement;
import com.google.gson.Gson;

import java.io.*;

/**
 * Reads and writes JSON models into/from POJO models.
 */
public class ModelSerializer {

    private static ModelSerializer INSTANCE = new ModelSerializer();

    private final Gson gson;

    public ModelSerializer() {
        gson = buildConfiguredObjectMapper();
    }

    public static ModelSerializer get() {
        return INSTANCE;
    }

    public static Gson buildConfiguredObjectMapper() {
        Gson gson = new Gson();
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//        objectMapper.setDateFormat(df);
//        objectMapper.setTimeZone(TimeZone.getDefault());
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return gson;
    }


    public <T> T deserialize(String jsonText, Class<T> valueClass) {
        return gson.fromJson(jsonText, valueClass);
    }

    public <T> T deserialize(Reader jsonText, Class<T> valueClass) {
        return gson.fromJson(jsonText, valueClass);
    }

    public String serialize(ModelElement modelElement) {
        return gson.toJson(modelElement);
    }

    public GroceryLists deserialize(File sourceFile) {
        try(Reader fr = new FileReader(sourceFile)) {
            if (sourceFile.canRead())
                return gson.fromJson(fr, GroceryLists.class);
            else
                return new GroceryLists();
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid JSON", e);
        }
    }

    public void serialize(File targetFile, GroceryLists groceryLists) {
        try(Writer fw = new FileWriter(targetFile)) {
            gson.toJson(groceryLists, fw);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid JSON", e);
        }
    }


}
