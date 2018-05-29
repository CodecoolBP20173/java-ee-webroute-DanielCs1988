package com.danielcs.webroute.controllers;

import static com.danielcs.webroute.server.WebRoute.Method;

import com.danielcs.webroute.data.MockData;
import com.danielcs.webroute.models.Superpower;
import com.danielcs.webroute.server.HttpUtils;
import com.danielcs.webroute.server.WebRoute;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class SuperpowerController {

    @WebRoute(path = "/superpower/<id:int>")
    public void getSuperpower(HttpExchange http, int id) throws IOException {
        Superpower power = null;
        try {
            power = MockData.superPowers.get(id);
        } catch (IndexOutOfBoundsException e) {}
        String resp = power != null ? Converter.convertToJson(power) : "Superpower could not be found.";
        HttpUtils.render(http, resp);
    }

    @WebRoute(path = "/superpower")
    public void getAllSuperpowers(HttpExchange http) throws IOException {
        String resp = Converter.convertToJson(MockData.superPowers);
        HttpUtils.render(http, resp);
    }

    @WebRoute(path = "/superpower", method = Method.POST)
    public void addSuperpower(HttpExchange http) throws  IOException {
        Superpower power = Converter.covertFromJson(HttpUtils.getRequestBody(http), Superpower.class);
        power.setId(MockData.superPowers.size());
        MockData.superPowers.add(power);
        HttpUtils.render(http, "Superpower was added with id: " + power.getId());
    }

    @WebRoute(path = "/superpower", method = Method.PUT)
    public void modifySuperpower(HttpExchange http) throws IOException {
        HttpUtils.render(http, "Superpower was updated!");
    }

    @WebRoute(path = "/superpower", method = Method.DELETE)
    public void deleteSuperpower(HttpExchange http) throws IOException {
        String id = http.getRequestURI().getQuery().split("=")[1];
        Superpower power = MockData.superPowers.get(Integer.valueOf(id));
        MockData.superPowers.remove(power);
        String resp = Converter.convertToJson(power);
        HttpUtils.render(http, resp);
    }
}
