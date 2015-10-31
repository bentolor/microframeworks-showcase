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

import bentolor.grocerylist.model.ModelElement;
import bentolor.grocerylist.persistence.ModelSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jodd.madvoc.meta.RenderWith;

@RenderWith(JsonResult.class)
public class JsonData {
    private final ObjectMapper objectMapper;
    private final ModelElement target;
    private final int status;

    {
        objectMapper = ModelSerializer.buildConfiguredObjectMapper();
    }

    public JsonData(ModelElement t) {
        target = t;
        status = -1;
    }

    public JsonData(int statusCode, String message) {
        target = new Message(message);
        status = statusCode;
    }

    public static JsonData notFound(String message) {
        return new JsonData(404, message);
    }

    public static JsonData noContent() {
        return new JsonData(204, null);
    }

    public static JsonData badRequest(String message) {
        return new JsonData(400, message);
    }

    public String toJsonString() {
        try {
            return target != null ? objectMapper.writeValueAsString(target) : "";
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid JSON to render");
        }
    }

    public int getStatus() {
        return status;
    }

    public static class Message implements ModelElement {
        String msg;

        public Message(String msg) {
            this.msg = msg;
        }
    }
}