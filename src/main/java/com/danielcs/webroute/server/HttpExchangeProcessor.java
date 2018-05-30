package com.danielcs.webroute.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

class HttpExchangeProcessor {

    private Gson converter;

    public HttpExchangeProcessor(Gson converter) {
        this.converter = converter;
    }

    Request getRequest(HttpExchange http) {
        return new BasicRequest(http, converter);
    }

    Response getResponse(HttpExchange http) {
        return new BasicResponse(http, converter);
    }
}
