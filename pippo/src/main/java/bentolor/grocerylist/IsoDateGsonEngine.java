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
package bentolor.grocerylist;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ro.pippo.gson.GsonEngine;

import java.util.Date;

/**
 * Dirty hack to adjust Pippos default configuration for Gson.
 * UX BROKEN DUE TO https://github.com/decebals/pippo/issues/414
 */
public class IsoDateGsonEngine extends GsonEngine {

    private final Gson gson;

    public IsoDateGsonEngine() {
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new ISO8601DateTypeAdapter()).create();
    }

    @Override
    public String toString(Object object) {
        return gson.toJson(object);
    }

    @Override
    public <T> T fromString(String content, Class<T> classOfT) {
        return gson.fromJson(content, classOfT);
    }
}
