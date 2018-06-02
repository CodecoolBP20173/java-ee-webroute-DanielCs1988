package com.danielcs.webroute.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class BasicRequest implements Request {

    private final HttpExchange http;
    private final Gson converter;

    BasicRequest(HttpExchange http, Gson converter) {
        this.http = http;
        this.converter = converter;
    }

    @Override
    public String getBody() {
        InputStream in = http.getRequestBody();
        Scanner s = new Scanner(in).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    @Override
    public <T> T getObjectFromBody(Class<T> type) {
        String rawInput = getBody();
        return rawInput.equals("") ? null : converter.fromJson(rawInput, type);
    }

    @Override
    public String getParam(String key) {
        // TODO: access x-www-form-etc encoded paramType
        return null;
    }

    @Override
    public Map<String, String> getParams() {
        return null;
    }

    @Override
    public String getHeader(String key) {
        return http.getRequestHeaders().getFirst(key);
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return http.getRequestHeaders();
    }

    @Override
    public InetSocketAddress getAddress() {
        return http.getRemoteAddress();
    }

    @Override
    public String getPath() {
        return http.getRequestURI().getPath();
    }

    @Override
    public String getFragment() {
        return http.getRequestURI().getFragment();
    }

    @Override
    public String getQueryParam(String key) {
        Matcher matcher = Pattern.compile(key + "=(.*)").matcher(http.getRequestURI().getQuery());
        return matcher.find() ? matcher.group(1) : null;
    }

    @Override
    public Map<String, String> getQueryParams() {
        String query = http.getRequestURI().getQuery();
        if (query == null) return null;

        Map<String, String> queryParams = new HashMap<>();
        for (String kvPair : query.split("&")) {
            String[] parts = kvPair.split("=");
            queryParams.put(parts[0], parts[1]);
        }
        return queryParams;
    }
}
