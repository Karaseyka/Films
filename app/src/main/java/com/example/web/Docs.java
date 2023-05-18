package com.example.web;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Docs {
    @SerializedName("docs")
    private List<Kinopoisk> movies;

    public List<Kinopoisk> getMovies() {
        return movies;
    }

}
