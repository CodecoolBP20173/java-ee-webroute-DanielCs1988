package com.danielcs.webroute.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Superhero {

    private int id;
    private String name;
    private String alias;
    private int age;
    private int powerlevel;
    private List<Superpower> superpowers = new ArrayList<>();

    public Superhero(int id, String name, String alias, int age, int powerlevel) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.age = age;
        this.powerlevel = powerlevel;
    }

    public Superhero(int id, String name, String alias, int age, int powerlevel, List<Superpower> superpowers) {
        this(id, name, alias, age, powerlevel);
        this.superpowers = superpowers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPowerlevel() {
        return powerlevel;
    }

    public void setPowerlevel(int powerlevel) {
        this.powerlevel = powerlevel;
    }

    public List<Superpower> getSuperpowers() {
        return Collections.unmodifiableList(superpowers);
    }

    public void addSuperpower(Superpower power) {
        this.superpowers.add(power);
    }

    public void removeSuperpower(Superpower power) {
        this.superpowers.remove(power);
    }

    @Override
    public String toString() {
        return "Superhero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", age=" + age +
                ", powerlevel=" + powerlevel +
                ", superpowers=" + superpowers +
                '}';
    }
}
