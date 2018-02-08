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
import com.google.gson.GsonBuilder;

import java.io.*;
import java.time.LocalDate;

/**
 * Reads and writes JSON models into/from POJO models.
 */
class ModelSerializer {

    private final Gson gson;

    ModelSerializer() {
        gson = buildConfiguredObjectMapper();
    }

    private static Gson buildConfiguredObjectMapper() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd").registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
    }


    <T> T deserialize(String jsonText, Class<T> valueClass) {
        return gson.fromJson(jsonText, valueClass);
    }

    <T> T deserialize(Reader jsonText, Class<T> valueClass) {
        return gson.fromJson(jsonText, valueClass);
    }

    String serialize(ModelElement modelElement) {
        return gson.toJson(modelElement);
    }

    GroceryLists deserialize(File sourceFile) {
        try (Reader fr = new FileReader(sourceFile)) {
            GroceryLists groceryLists = null;
            if (sourceFile.canRead()) {
                groceryLists = gson.fromJson(fr, GroceryLists.class);
            }
            return groceryLists != null ? groceryLists : new GroceryLists();
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid JSON", e);
        }
    }

    void serialize(File targetFile, GroceryLists groceryLists) {
        try (Writer fw = new FileWriter(targetFile)) {
            gson.toJson(groceryLists, fw);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid JSON", e);
        }
    }


}
