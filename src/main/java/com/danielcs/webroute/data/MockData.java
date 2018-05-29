package com.danielcs.webroute.data;

import com.danielcs.webroute.models.*;
import java.util.*;

public class MockData {

    public static List<Superpower> superPowers = new ArrayList<>(Arrays.asList(
            new Superpower(0, "flight", 9001, "https://vignette.wikia.nocookie.net/powerlisting/images/e/ec/Flying-heroes.jpg/revision/latest?cb=20110813212016"),
            new Superpower(1, "super strength", 12000, "http://img1.looper.com/img/gallery/why-marvel-wont-give-hulk-a-movie/intro.jpg"),
            new Superpower(2, "exoskeleton", 12000, "https://ctd-thechristianpost.netdna-ssl.com/en/full/30892/iron-man-4.jpg"),
            new Superpower(3, "lightning", 15000, "https://i.pinimg.com/originals/1f/f8/9f/1ff89f5580906e0fd921dfca88a259c7.jpg")
    ));

    public static List<Superhero> superHeroes = new ArrayList<>(Arrays.asList(
            new Superhero(0, "Tony Stark", "Iron Man", 45, 9001, new ArrayList<>(Arrays.asList(
                    superPowers.get(0), superPowers.get(2)
            ))),
            new Superhero(1, "Thor Odinsson", "Thor", 1450, 15000, new ArrayList<>(Arrays.asList(
                    superPowers.get(0), superPowers.get(1), superPowers.get(3)
            ))),
            new Superhero(2, "Dr. Banner", "Hulk", 47, 10500, new ArrayList<>(Collections.singletonList(
                    superPowers.get(1)
            )))
    ));

}
