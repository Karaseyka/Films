package com.example.films.ui.main.group;

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

import com.example.adapters.AdapterUser;
import com.example.films.R;
import com.example.films.ui.main.film.FilmsListActivity;
import com.example.films.ui.main.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {
    ArrayList<User> us = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        String grId = getIntent().getStringExtra("id");
        RecyclerView rv = (RecyclerView) findViewById(R.id.user_recycler);
        ImageView iv = findViewById(R.id.back);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switcher = new Intent(UsersActivity.this, GroupActivity.class).putExtra("id", getIntent().getStringExtra("id"));
                startActivity(switcher);
                finish();
            }
        });
        db.child("Group").child(grId).child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i: snapshot.getChildren()){
                    String user = i.getKey();
                    Log.d("fhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh", user);
                    db.child("Users").child(user).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user1 = snapshot.getValue(User.class);
                            Log.d("fhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh", user1.name);

                            us.add(user1);
                            AdapterUser adapter = new AdapterUser(UsersActivity.this, us, 1);
                            rv.setAdapter(adapter);
                            rv.setLayoutManager(new LinearLayoutManager(UsersActivity.this));
                            adapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

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