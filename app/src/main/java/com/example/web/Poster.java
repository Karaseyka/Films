package com.example.web;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Poster  implements Serializable {

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }
}