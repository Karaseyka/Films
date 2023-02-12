package com.example.films;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
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

        mdb.child("Film").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList <Film> a = new ArrayList<>();
                for (DataSnapshot ds: snapshot.getChildren()){
                    a.add(ds.getValue(Film.class));
                }
                RecyclerView rv = (RecyclerView) findViewById(R.id.FilmsList) ;
                AdapterFilms ad = new AdapterFilms(FilmsList.this, a);
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