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
package bentolor.hellospark;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.jade.JadeTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public final class HelloWorldService {

    private HelloWorldService() {
    }

    public static void main(String[] args) {
        port(8080);

        get("/hello", "text/html", (req, res)
                -> "<!DOCTYPE html><html><h1>" +
                "Hello World</h1></html>");
        get("/hello", (req, res) -> "Hello World");

        get("/env/:varname", ((req, res)
                -> System.getenv(req.params("varname"))));

        get("/greet", "text/html", (req, res) -> {
            Map<String, String> values = new HashMap<>();
            String name = req.queryParams("name");
            values.put("name", name != null ? name : "Anon");
            return new ModelAndView(values, "greet");
        }, new JadeTemplateEngine());

        get("/protected", HelloWorldService::unauthorized);
    }

    public static Object unauthorized(Request request, Response response) {
        halt(200, "Go away!");
        return null;
    }
}