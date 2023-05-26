package com.example.web;



import android.util.Log;

import androidx.annotation.NonNull;

import com.example.films.ui.main.film.Film;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Request implements Runnable {
    public static final String BASE_URL = "https://api.kinopoisk.dev/v1.3/";
    private DatabaseReference mdb1;


    @Override
    public void run() {
        mdb1 = FirebaseDatabase.getInstance().getReference();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService sv = retrofit.create(ApiService.class);
        sv.getFilms().enqueue(new Callback<Docs>() {
            @Override
            public void onResponse(@NonNull Call<Docs> call, @NonNull Response<Docs> response) {
                if(response.body().getMovies().size() != 0 && response.body().getMovies() != null) {
                    for (int i = 0; i < response.body().getMovies().size(); i++) {
                        String a = mdb1.child("Film").push().getKey();
                        mdb1.child("Film")
                                .child(a)
                                .setValue(new Film(response.body().getMovies().get(i).getName(),
                                        response.body().getMovies().get(i).getPoster().getUrl(),
                                        "Aboba", a));
                    }
                }
            }


            @Override
            public void onFailure(Call<Docs> call, Throwable t) {
                Log.d("fjjghhgjhjh", call.toString());

            }
        });
    }
}
