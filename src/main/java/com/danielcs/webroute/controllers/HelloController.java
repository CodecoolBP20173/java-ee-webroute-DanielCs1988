package com.danielcs.webroute.controllers;

import com.danielcs.webroute.server.HttpUtils;
import com.danielcs.webroute.server.WebRoute;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class HelloController {

    @WebRoute(path = "/")
    public void doHello(HttpExchange http) throws IOException {
        HttpUtils.render(http, "Hello World!");
    }
}
