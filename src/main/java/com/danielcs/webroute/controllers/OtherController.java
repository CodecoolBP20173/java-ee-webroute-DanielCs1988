package com.danielcs.webroute.controllers;

import static com.danielcs.webroute.server.WebRoute.Method;

import com.danielcs.webroute.server.WebRoute;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class OtherController {

    @WebRoute(path = "/other")
    public void doGet(HttpExchange http) throws IOException {
        String resp = "<h1>This is an even better route! //GET</h1>";
        http.sendResponseHeaders(200, resp.getBytes().length);
        OutputStream out = http.getResponseBody();
        out.write(resp.getBytes());
        out.close();
    }

    @WebRoute(path = "/other", method = Method.POST)
    public void doPost(HttpExchange http) throws IOException {
        String resp = "<h1>This is an even better route! //POST</h1>";
        http.sendResponseHeaders(200, resp.getBytes().length);
        OutputStream out = http.getResponseBody();
        out.write(resp.getBytes());
        out.close();
    }

    @WebRoute(path = "/other", method = Method.PUT)
    public void doPut(HttpExchange http) throws IOException {
        String resp = "<h1>This is an even better route! //PUT</h1>";
        http.sendResponseHeaders(200, resp.getBytes().length);
        OutputStream out = http.getResponseBody();
        out.write(resp.getBytes());
        out.close();
    }

    @WebRoute(path = "/other", method = Method.DELETE)
    public void doDelete(HttpExchange http) throws IOException {
        String resp = "<h1>This is an even better route! //DELETE</h1>";
        http.sendResponseHeaders(200, resp.getBytes().length);
        OutputStream out = http.getResponseBody();
        out.write(resp.getBytes());
        out.close();
    }
}
