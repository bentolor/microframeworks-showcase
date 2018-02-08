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

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** Gson Adapter to support {@link LocalDate} objects. */
public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    @Override public void write(JsonWriter out, LocalDate date) throws IOException {
        out.value(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    @Override public LocalDate read(JsonReader in) throws IOException {
        return LocalDate.parse(in.nextString(), DateTimeFormatter.ISO_LOCAL_DATE);
    }
}