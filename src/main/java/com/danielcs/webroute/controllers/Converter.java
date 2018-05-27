package com.danielcs.webroute.controllers;

import com.google.gson.Gson;

public class Converter {

    private static Gson gson = new Gson();

    public static String convertToJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T covertFromJson(String json, Class<T> typeOfT) {
        return gson.fromJson(json, typeOfT);
    }
}
