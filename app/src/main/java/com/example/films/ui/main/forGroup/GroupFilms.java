package com.example.films.ui.main.forGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adapters.AdapterFilms;
import com.example.films.R;
import com.example.films.ui.main.forFilm.Film;
import com.example.films.ui.main.forFilm.FilmsList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupFilms extends AppCompatActivity {

    private DatabaseReference mdb;
    private FirebaseAuth ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_films);
        mdb = FirebaseDatabase.getInstance().getReference();
        ImageView iv = (ImageView) findViewById(R.id.imageView4);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switcher = new Intent(GroupFilms.this, GroupActivity.class).putExtra("id", getIntent().getStringExtra("id"));
                startActivity(switcher);
            }
        });

        mdb.child("Group").child(getIntent().getStringExtra("id")).child("GrFilms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Film> a = new ArrayList<>();
                for (DataSnapshot ds: snapshot.getChildren()){
                    String fl1 = ds.getValue().toString();
                    mdb.child("Film").child(fl1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            Film fl = snapshot.getValue(Film.class);

                            RecyclerView rv = (RecyclerView) findViewById(R.id.FilmsList) ;

                            a.add(fl);
                            AdapterFilms ad = new AdapterFilms(GroupFilms.this, a, getIntent().getStringExtra("id"), 2);
                            rv.setAdapter(ad);
                            rv.setLayoutManager(new LinearLayoutManager(GroupFilms.this));
                            ad.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            throw error.toException(); // never ignore errors

                        }
                    });
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}