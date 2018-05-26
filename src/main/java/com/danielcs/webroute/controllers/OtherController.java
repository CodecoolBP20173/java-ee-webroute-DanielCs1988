package com.danielcs.webroute.controllers;

import com.danielcs.webroute.server.WebRoute;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class OtherController implements HttpHandler {

    @WebRoute(path = "/other")
    public void handle(HttpExchange http) throws IOException {
        String resp = "<h1>This is an even better route!</h1>";
        http.sendResponseHeaders(200, resp.getBytes().length);
        OutputStream out = http.getResponseBody();
        out.write(resp.getBytes());
        out.close();
    }
}
