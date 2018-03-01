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
package persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
    ObjectMapper mapper;
    @Inject
    @Named("json-dateformat")
    DateFormat df;

    @Start(order = 90)
    public void configureObjectMapper() {
        mapper.registerModule(new JavaTimeModule());
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
