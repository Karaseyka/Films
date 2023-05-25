package com.example.films.ui.main.film;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.adapters.AdapterUser;
import com.example.films.R;
import com.example.adapters.AdapterFilms;
import com.example.films.ui.main.group.GroupActivity;
import com.example.films.ui.main.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class FilmsListActivity extends AppCompatActivity {
    private DatabaseReference mdb;
    private FirebaseAuth ma;
    ArrayList <Film> a = new ArrayList<>();
    ArrayList <String> nt = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_films_list);
        mdb = FirebaseDatabase.getInstance().getReference();
        ImageView iv = (ImageView) findViewById(R.id.imageView4);
        SearchView sv = (SearchView) findViewById(R.id.search_view);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                nt.clear();
                nt.add(newText);
                        mdb.child("Film").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                a.clear();
                                for (DataSnapshot i : snapshot.getChildren()) {
                                    Film us = i.getValue(Film.class);
//                                Log.d("fhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh", nt.get(0));
                                    if (us.name.toLowerCase().contains(nt.get(0).toLowerCase())) {
                                        a.add(us);
                                    }
                                }
                                RecyclerView rv = (RecyclerView) findViewById(R.id.FilmsList);
                                AdapterFilms adapter = new AdapterFilms(FilmsListActivity.this, a, getIntent().getStringExtra("id"), 1);
                                rv.setAdapter(adapter);
                                rv.setLayoutManager(new LinearLayoutManager(FilmsListActivity.this));
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                return true;
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switcher = new Intent(FilmsListActivity.this, GroupActivity.class).putExtra("id", getIntent().getStringExtra("id"));
                startActivity(switcher);
                finish();
            }
        });

        mdb.child("Film").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds: snapshot.getChildren()){
                    a.add(ds.getValue(Film.class));
                }
                RecyclerView rv = (RecyclerView) findViewById(R.id.FilmsList) ;
                AdapterFilms ad = new AdapterFilms(FilmsListActivity.this, a, getIntent().getStringExtra("id"), 1);
                rv.setAdapter(ad);
                rv.setLayoutManager(new LinearLayoutManager(FilmsListActivity.this));
                ad.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}