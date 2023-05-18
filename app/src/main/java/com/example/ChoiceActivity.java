package com.example;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapters.AdapterFilms;
import com.example.films.R;
import com.example.films.ui.main.Dec;
import com.example.films.ui.main.film.Film;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ChoiceActivity extends AppCompatActivity {
    private DatabaseReference mdb;
    private FirebaseAuth ma;
    ArrayList <Film> a;
    ArrayList <String> m = new ArrayList<>();
    AdapterFilms ad;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choise_film);
        ma = FirebaseAuth.getInstance();
        mdb = FirebaseDatabase.getInstance().getReference();

        mdb.child("Film").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                a = new ArrayList<>();
                for (DataSnapshot ds: snapshot.getChildren()){
                    a.add(ds.getValue(Film.class));

                }
                RecyclerView rv = (RecyclerView) findViewById(R.id.oneByOne) ;
                Collections.shuffle(a);
                ad = new AdapterFilms(ChoiceActivity.this, a, "d", 3);
                rv.setAdapter(ad);
                rv.addItemDecoration(new Dec());
                new ItemTouchHelper(simpleItemTouchCallback).attachToRecyclerView(rv);
                rv.setLayoutManager(new LinearLayoutManager(ChoiceActivity.this) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }});
                rv.setNestedScrollingEnabled(false);
                ad.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Toast.makeText(getApplicationContext(), "on Move", Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            int position = viewHolder.getAbsoluteAdapterPosition();
            Film x = a.get(position);

            if(swipeDir == ItemTouchHelper.LEFT){
                mdb.child("Users").child(ma.getCurrentUser().getUid().toString()).child("Dislike").child(x.id).removeValue();
                mdb.child("Users").child(ma.getCurrentUser().getUid().toString()).child("Like").child(x.id).setValue(x.id);
            } else if(swipeDir == ItemTouchHelper.RIGHT) {
                mdb.child("Users").child(ma.getCurrentUser().getUid().toString()).child("Like").child(x.id).removeValue();
                mdb.child("Users").child(ma.getCurrentUser().getUid().toString()).child("Dislike").child(x.id).setValue(x.id);
            }
            a.remove(position);
            ad.notifyItemRemoved(position);;

        }
    };
//    public void p(){
//        mdb.child("Users").child(ma.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                m = new ArrayList<>();
//                for(DataSnapshot i : snapshot.child("Like").getChildren()){
//                    m.add(i.getKey());
//                    Log.d("nbvcfdfghjkjhgfdfghjkjhgf", i.getKey());
//                }
//                for(DataSnapshot i : snapshot.child("Dislike").getChildren()){
//                    m.add(i.getKey());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}
