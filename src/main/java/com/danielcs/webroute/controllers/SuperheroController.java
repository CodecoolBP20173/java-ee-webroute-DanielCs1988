package com.danielcs.webroute.controllers;

import com.danielcs.webroute.data.MockData;
import com.danielcs.webroute.models.Superhero;
import com.danielcs.webroute.server.HttpUtils;
import com.danielcs.webroute.server.WebRoute;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Optional;

public class SuperheroController {

    @WebRoute(path = "/superhero/<alias>")
    public void getHeroByAlias(HttpExchange http, String alias) throws IOException {
        Optional<Superhero> superhero = MockData.superHeroes.stream()
                .filter(hero -> hero.getAlias().equals(alias))
                .findFirst();
        String resp = superhero.isPresent() ? Converter.convertToJson(superhero) : "Hero could not be found.";
        HttpUtils.render(http, resp);
    }

    @WebRoute(path = "/superhero")
    public void getAllSuperheroes(HttpExchange http) throws IOException {
        String resp = Converter.convertToJson(MockData.superHeroes);
        HttpUtils.render(http, resp);
    }

    @WebRoute(path = "/superhero", method = WebRoute.Method.POST)
    public void addSuperhero(HttpExchange http) throws  IOException {
        Superhero hero = Converter.covertFromJson(HttpUtils.getRequestBody(http), Superhero.class);
        hero.setId(MockData.superHeroes.size());
        MockData.superHeroes.add(hero);
        HttpUtils.render(http, "Superhero was added with id: " + hero.getId());
    }

    @WebRoute(path = "/superhero", method = WebRoute.Method.PUT)
    public void modifySuperhero(HttpExchange http) throws IOException {
        HttpUtils.render(http, "Superhero was updated!");
    }

    @WebRoute(path = "/superhero", method = WebRoute.Method.DELETE)
    public void deleteSuperhero(HttpExchange http) throws IOException {
        String id = http.getRequestURI().getQuery().split("=")[1];
        Superhero hero = MockData.superHeroes.get(Integer.valueOf(id));
        MockData.superHeroes.remove(hero);
        String resp = Converter.convertToJson(hero);
        HttpUtils.render(http, resp);
    }

}
