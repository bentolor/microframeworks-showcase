package bentolor.hellospark;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.jade.JadeTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class HelloWorldService {

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
      String n = req.queryParams("name");
      values.put("name", n != null ? n : "Anon");
      return new ModelAndView(values, "greet");
    }, new JadeTemplateEngine());

    get("/protected", HelloWorldService::unauthorized);
  }

  public static Object unauthorized(Request request, Response response) throws Exception {
    halt(200, "Go away!");
    return null;
  }
}