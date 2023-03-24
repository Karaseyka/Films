package com.example.films.ui.main.forGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.films.ui.main.forFilm.Film;
import com.example.films.ui.main.forFilm.FilmsList;
import com.example.films.R;
import com.example.films.ui.main.fragments.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class GroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        DatabaseReference mbd = FirebaseDatabase.getInstance().getReference();

        Button bt = (Button) findViewById(R.id.add);
        Button bt1 = (Button) findViewById(R.id.check);
        Button bt2 = (Button) findViewById(R.id.button4);
        TextView tv = (TextView) findViewById(R.id.textView13);
        TextView tv1 = (TextView) findViewById(R.id.textView12);
        TextView tv3 = (TextView) findViewById(R.id.textView15);
        ImageView iv = (ImageView) findViewById(R.id.imageView5);
        ImageView iv1 = (ImageView) findViewById(R.id.imageView6);
        iv1.setOnClickListener(view -> {
            Intent switcher = new Intent(GroupActivity.this, MainActivity.class);
            startActivity(switcher);
        });
        tv.setText(getIntent().getStringExtra("id"));

        mbd.child("Group").child(getIntent().getStringExtra("id")).child("FilmOfGroup").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String name = snapshot.getValue().toString();
                    mbd.child("Film").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Film fl = snapshot.getValue(Film.class);
                            tv3.setText(fl.name);
                            Picasso.get().load(fl.url).fit()
                                    .centerCrop().into(iv);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }catch (Exception e){}



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        mbd.child("Group").child(getIntent().getStringExtra("id")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(Group.class).name;
                tv1.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switcher = new Intent(GroupActivity.this, FilmsList.class).putExtra("id", tv.getText());
                startActivity(switcher);
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switcher = new Intent(GroupActivity.this, GroupFilms.class).putExtra("id", tv.getText());
                startActivity(switcher);

            }
        });
         bt2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 mbd.child("Group").child(getIntent().getStringExtra("id")).child("GrFilms").addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         ArrayList<String> grfl = new ArrayList<>();
                         for(DataSnapshot ds: snapshot.getChildren()){
                             grfl.add(ds.getKey());
                         }
                         if(grfl.size() > 0) {
                             int rd = new Random().nextInt(grfl.size());
                             mbd.child("Group").child(getIntent().getStringExtra("id")).child("FilmOfGroup").setValue(grfl.get(rd));
                             mbd.child("Film").child(grfl.get(rd)).addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                                     Film fl = snapshot.getValue(Film.class);
                                     assert fl != null;
                                     tv3.setText(fl.name);
                                     Picasso.get().load(fl.url).fit()
                                             .centerCrop().into(iv);

                                 }

                                 @Override
                                 public void onCancelled(@NonNull DatabaseError error) {

                                 }
                             });
                         } else{
                             Toast.makeText(GroupActivity.this, "Ой, кажется вы не выбрали фильмы", Toast.LENGTH_LONG).show();
                         }
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });
             }
         });

    }
}