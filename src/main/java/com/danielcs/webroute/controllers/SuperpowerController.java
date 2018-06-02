package com.danielcs.webroute.controllers;

import static com.danielcs.webroute.server.WebRoute.Method;

import com.danielcs.webroute.data.MockData;
import com.danielcs.webroute.models.Superpower;
import com.danielcs.webroute.server.*;

import java.io.IOException;

public class SuperpowerController {

    @WebRoute(path = "/superpower/<id:int>")
    public void getSuperpower(Request req, Response resp, int id) throws IOException {
        Superpower power = null;
        try {
            power = MockData.superPowers.get(id);
        } catch (IndexOutOfBoundsException e) {}
        Object rsp = power != null ? power : "Superpower could not be found.";
        resp.sendObject(rsp);
    }

    @WebRoute(path = "/superpower")
    public void getAllSuperpowers(Request req, Response resp) throws IOException {
        resp.sendObject(MockData.superPowers);
    }

    @WebRoute(path = "/superpower", method = Method.POST, paramType = WebRoute.ParamType.JSON, model = Superpower.class)
    public void addSuperpower(Superpower power, Response resp) throws  IOException {
        power.setId(MockData.superPowers.size());
        MockData.superPowers.add(power);
        resp.sendString("Superpower was added with id: " + power.getId());
    }

    @WebRoute(path = "/superpower", method = Method.PUT)
    public void modifySuperpower(Request req, Response resp) throws IOException {
        resp.sendString("Superpower was updated!");
    }

    @WebRoute(path = "/superpower", method = Method.DELETE)
    public void deleteSuperpower(Request req, Response resp) throws IOException {
        String id = req.getQueryParam("id");
        Superpower power = MockData.superPowers.get(Integer.valueOf(id));
        MockData.superPowers.remove(power);
        resp.sendObject(power);
    }
}
