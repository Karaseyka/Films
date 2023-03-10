package com.example.films.ui.main.forFilm;

public class Film {

    public String id, name, url, txt;
    public Film(){}

    public Film(String name, String url, String txt, String id){
        this.id = id;
        this.name = name;
        this.url = url;
        this.txt = txt;
    }
}
