package com.danielcs.webroute.models;

public class Superpower {

    private int id;
    private String name;
    private int powerlevel;
    private String imagePath;

    public Superpower(int id, String name, int powerlevel, String imagePath) {
        this.id = id;
        this.name = name;
        this.powerlevel = powerlevel;
        this.imagePath = imagePath;
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

    public int getPowerlevel() {
        return powerlevel;
    }

    public void setPowerlevel(int powerlevel) {
        this.powerlevel = powerlevel;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Superpower{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", powerlevel=" + powerlevel +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
