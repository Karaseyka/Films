package com.example.web;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;

public class Kinopoisk implements Serializable {
    @SerializedName("name")
    private String name;

    @SerializedName("poster")
    private Poster poster;

    public String getName() {
        return name;
    }

    public Poster getPoster() {
        return poster;
    }

    public Kinopoisk(String name, Poster poster) {
        this.name = name;
        this.poster = poster;
    }


}
