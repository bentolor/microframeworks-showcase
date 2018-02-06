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
package bentolor.grocerylist.service;

import spark.Route;

/**
 * A utility class for shorthand methods for handling Sparks {@link spark.Request} and {@link spark.Response} instances.
 */
final class ResponseCreator {

    private ResponseCreator() {
    }

    static Route ok(String body) {
        return (req, res) -> {
            res.status(200);
            res.type("application/json");

            return body;
        };
    }

    static Route notFound(String message) {
        return (req, res) -> {
            res.status(404);
            res.type("text/plain");

            return message;
        };
    }

    static Route noContent() {
        return (req, res) -> {
            res.status(204);
            return "";
        };
    }

    static Route badRequest(String message) {
        return (req, res) -> {
            res.status(400);
            res.type("text/plain");

            return message;
        };
    }
}
