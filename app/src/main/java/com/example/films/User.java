package com.example.films;

public class User {
    public String id, name, email, groups, love_films, auth, reg_date;

    public User() {
    }

    public User(String id, String name, String email, String groups, String love_films, String auth, String reg_date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.groups = groups;
        this.love_films = love_films;
        this.auth = auth;
        this.reg_date = reg_date;
    }
}
