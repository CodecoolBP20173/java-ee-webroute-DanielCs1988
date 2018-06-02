package com.danielcs.webroute.controllers;

import com.danielcs.webroute.server.Request;
import com.danielcs.webroute.server.Response;
import com.danielcs.webroute.server.WebRoute;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class HelloController {

    @WebRoute(path = "/", paramType = WebRoute.ParamType.NONE)
    public void doHello(HttpExchange legacy) throws IOException {
        String resp = "Hello World!";
        legacy.sendResponseHeaders(200, resp.getBytes().length);
        legacy.getResponseBody().write(resp.getBytes());
        legacy.close();
    }

    @WebRoute(path = "/", method = WebRoute.Method.POST)
    public void postie(Request req, Response response) throws IOException {
        System.out.println("URL: " + req.getQueryParams());
        System.out.println("Body: " + req.getParams());
        System.out.println("1 param: " + req.getParam("username"));
        System.out.println("Another: " + req.getParam("password"));
        response.sendString("KEK");
    }
}
