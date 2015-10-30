package bentolor.grocerylist.service;

import spark.Route;

/**
 * A utility class for shorthand methods for handling Sparks {@link spark.Request} and {@link spark.Response} instances.
 */
final class ResponseCreator {

    private ResponseCreator() {}

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
