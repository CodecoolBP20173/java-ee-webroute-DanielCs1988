package com.danielcs.webroute.controllers;

import com.danielcs.webroute.data.MockData;
import com.danielcs.webroute.models.Superhero;
import com.danielcs.webroute.server.*;

import java.io.IOException;
import java.util.Optional;

public class SuperheroController {

    @WebRoute(path = "/superhero/<alias>")
    public void getHeroByAlias(Request req, Response resp, String alias) throws IOException {
        Optional<Superhero> superhero = MockData.superHeroes.stream()
                .filter(hero -> hero.getAlias().equals(alias))
                .findFirst();
        Object rsp = superhero.isPresent() ? superhero.get() : "Hero could not be found.";
        System.out.println(req.getPath() + "\n" + req.getAddress() + "\n" + req.getHeaders());
        resp.sendObject(rsp);
    }

    @WebRoute(path = "/superhero")
    public void getAllSuperheroes(Request req, Response resp) throws IOException {
        System.out.println(req.getQueryParams());
        resp.sendObject(MockData.superHeroes);
    }

    @WebRoute(path = "/superhero", method = WebRoute.Method.POST, params = WebRoute.ParseMode.JSON, input = Superhero.class)
    public void addSuperhero(Superhero hero, Response response) throws  IOException {
        System.out.println(hero + " just arrived!");
        hero.setId(MockData.superHeroes.size());
        MockData.superHeroes.add(hero);
        response.addHeader("Test", "Works");
        response.sendObject(hero);
    }

    @WebRoute(path = "/superhero", method = WebRoute.Method.PUT)
    public void modifySuperhero(Request req, Response resp) throws IOException {
        resp.sendString("Superhero was updated!");
    }

    @WebRoute(path = "/superhero", method = WebRoute.Method.DELETE)
    public void deleteSuperhero(Request req, Response resp) throws IOException {
        String id = req.getQueryParam("id");
        Superhero hero = MockData.superHeroes.get(Integer.valueOf(id));
        MockData.superHeroes.remove(hero);
        resp.sendObject(hero);
    }

}
