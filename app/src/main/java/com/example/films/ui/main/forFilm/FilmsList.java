package com.example.films.ui.main.forFilm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.films.R;
import com.example.adapters.AdapterFilms;
import com.example.films.ui.main.forGroup.GroupActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FilmsList extends AppCompatActivity {
    private DatabaseReference mdb;
    private FirebaseAuth ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_films_list);
        mdb = FirebaseDatabase.getInstance().getReference();
        ImageView iv = (ImageView) findViewById(R.id.imageView4);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switcher = new Intent(FilmsList.this, GroupActivity.class).putExtra("id", getIntent().getStringExtra("id"));
                startActivity(switcher);
            }
        });

        mdb.child("Film").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList <Film> a = new ArrayList<>();
                for (DataSnapshot ds: snapshot.getChildren()){
                    a.add(ds.getValue(Film.class));
                }
                RecyclerView rv = (RecyclerView) findViewById(R.id.FilmsList) ;
                AdapterFilms ad = new AdapterFilms(FilmsList.this, a, getIntent().getStringExtra("id"), 1);
                rv.setAdapter(ad);
                rv.setLayoutManager(new LinearLayoutManager(FilmsList.this));
                ad.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}