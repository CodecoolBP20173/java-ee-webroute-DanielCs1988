package com.danielcs.webroute.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

class BasicResponse implements Response {

    private final HttpExchange http;
    private final Gson converter;

    BasicResponse(HttpExchange http, Gson converter) {
        this.http = http;
        this.converter = converter;
    }

    @Override
    public void sendString(String content) throws IOException {
        http.sendResponseHeaders(200, content.getBytes().length);
        OutputStream out = http.getResponseBody();
        out.write(content.getBytes());
        out.close();
    }

    @Override
    public void sendObject(Object object) throws IOException {
        String json = converter.toJson(object);
        sendString(json);
    }

    @Override
    public void addHeader(String key, String value) {
        http.getResponseHeaders().add(key, value);
    }
}
