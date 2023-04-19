package com.example.films.ui.main.forGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adapters.AdapterFilms;
import com.example.films.R;
import com.example.films.ui.main.forFilm.Film;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class GroupFilmsActivity extends AppCompatActivity {

    private DatabaseReference mdb;
    private FirebaseAuth ma;
    public ArrayList<Film> a;
    AdapterFilms ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_films);
        mdb = FirebaseDatabase.getInstance().getReference();
        ImageView iv = (ImageView) findViewById(R.id.imageView4);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switcher = new Intent(GroupFilmsActivity.this, GroupActivity.class).putExtra("id", getIntent().getStringExtra("id"));
                startActivity(switcher);
            }
        });

        mdb.child("Group").child(getIntent().getStringExtra("id")).child("GrFilms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                a = new ArrayList<>();
                for (DataSnapshot ds: snapshot.getChildren()){
                    String fl1 = ds.getValue().toString();
                    mdb.child("Film").child(fl1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            Film fl = snapshot.getValue(Film.class);

                            RecyclerView rv = (RecyclerView) findViewById(R.id.FilmsList) ;

                            a.add(fl);
                            ad = new AdapterFilms(GroupFilmsActivity.this, a, getIntent().getStringExtra("id"), 2);
                            rv.setAdapter(ad);
                            rv.setLayoutManager(new LinearLayoutManager(GroupFilmsActivity.this));
                            new ItemTouchHelper(simpleItemTouchCallback).attachToRecyclerView(rv);
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
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Toast.makeText(getApplicationContext(), "on Move", Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            int position = viewHolder.getAbsoluteAdapterPosition();
            Film x = a.get(position);
            mdb.child("Group").child(getIntent().getStringExtra("id")).child("FilmOfGroup").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (Objects.equals(snapshot.child("Name").getValue(String.class), x.id)){
                        mdb.child("Group").child(getIntent().getStringExtra("id")).child("FilmOfGroup").removeValue();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            mdb.child("Group").child(getIntent().getStringExtra("id")).child("GrFilms").child(x.id).removeValue();
            a.remove(position);
            ad.notifyDataSetChanged();

        }
    };
}