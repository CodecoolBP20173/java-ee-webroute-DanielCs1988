package com.danielcs.webroute.controllers;

import com.danielcs.webroute.server.WebRoute;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class TestController {

    @WebRoute(path = "/")
    public void doGet(HttpExchange http) throws IOException {
        String resp = "Hello World!";
        http.sendResponseHeaders(200, resp.getBytes().length);
        OutputStream out = http.getResponseBody();
        out.write(resp.getBytes());
        out.close();
    }
}
