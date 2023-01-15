package com.example.films;

public class User {
    public String id, name, email, gr_count, love_film, auth;

    public User() {
    }

    public User(String id, String name, String email, String gr_count, String love_film, String auth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gr_count = gr_count;
        this.love_film = love_film;
        this.auth = auth;
    }
}
