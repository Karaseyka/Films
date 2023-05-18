package com.example.web;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie?token=CVP2EHN-RFZMPYY-PKD3RQ7-R3FPQ3Z&field")
    Call<Docs> getFilm();

    @GET("movie?token=CVP2EHN-RFZMPYY-PKD3RQ7-R3FPQ3Z&limit=300")
    Call<Docs> getFilms();

    @GET("movie?token=CVP2EHN-RFZMPYY-PKD3RQ7-R3FPQ3Z&field=id")
    Call<Docs> getFilmById(
            @Query("id") int id
    );
}
