package com.danielcs.webroute.controllers;

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
}
