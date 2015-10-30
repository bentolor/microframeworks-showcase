package controllers;

import ninja.Result;

import static ninja.Results.html;

public class HelloWorldController {

    public Result helloWorld() {
        return html();      // Renders by convention: /HelloWorldController/helloWorld.ftl.html
    }

}
