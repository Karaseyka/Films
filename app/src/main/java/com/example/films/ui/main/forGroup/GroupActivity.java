package com.example.films.ui.main.forGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GroupActivity extends AppCompatActivity {
    ArrayList<String> liked = new ArrayList<>();
    ArrayList<String> dis = new ArrayList<>();
    ArrayList<String> ney = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Button bt = (Button) findViewById(R.id.add);
        Button bt1 = (Button) findViewById(R.id.check);
        Button bt2 = (Button) findViewById(R.id.button4);
        TextView tv = (TextView) findViewById(R.id.textView13);
        TextView tv1 = (TextView) findViewById(R.id.textView12);
        TextView tv3 = (TextView) findViewById(R.id.textView15);
        TextView tv4 = (TextView) findViewById(R.id.text_like);
        TextView tv5 = (TextView) findViewById(R.id.text_ney);
        TextView tv6 = (TextView) findViewById(R.id.text_dis);
        ImageView iv = (ImageView) findViewById(R.id.imageView5);
        ImageView iv1 = (ImageView) findViewById(R.id.imageView6);
        ImageView iv2 = (ImageView) findViewById(R.id.image_like);
        ImageView iv3 = (ImageView) findViewById(R.id.image_dis);
        ImageView iv4 = (ImageView) findViewById(R.id.image_ney);
        ImageView del = (ImageView) findViewById(R.id.del);
        String grId = getIntent().getStringExtra("id");
        FirebaseAuth ma;
        ma = FirebaseAuth.getInstance();
        DatabaseReference mbd = FirebaseDatabase.getInstance().getReference();

        del.setOnClickListener(view -> {
            mbd.child("Group").child(grId).child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int i = 0;
                    try {
                        for(DataSnapshot dt: dataSnapshot.getChildren()){
                            i += 1;
                        }
                        if(i <= 1){
                            mbd.child("Group").child(grId).removeValue();
                            mbd.child("Users").child(ma.getCurrentUser().getUid()).child("Group").child(grId).removeValue();
                        } else{
                            mbd.child("Users").child(ma.getCurrentUser().getUid()).child("Group").child(grId).removeValue();
                            mbd.child("Group").child(grId).child("Users").child(ma.getCurrentUser().getUid()).removeValue();
                        }
                        Intent switcher = new Intent(GroupActivity.this, MainActivity.class);
                        startActivity(switcher);
                    }catch (Exception e){
                        mbd.child("Group").child(grId).removeValue();
                        mbd.child("Users").child(ma.getCurrentUser().getUid()).child("Group").child(grId).removeValue();
                        Intent switcher = new Intent(GroupActivity.this, MainActivity.class);
                        startActivity(switcher);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        });

        mbd.child("Group").child(grId).child("FilmOfGroup").child("Likes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                liked.clear();
                try {
                    int count = 0;
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        liked.add(ds.getKey());count += 1;}
                    tv4.setText(String.valueOf(count));

                }catch (Exception e){}

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        mbd.child("Group").child(grId).child("FilmOfGroup").child("Dis").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dis.clear();
                try {
                    int count = 0;
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        dis.add(ds.getKey());count += 1;}
                    tv6.setText(String.valueOf(count));

                }catch (Exception e){}

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        mbd.child("Group").child(grId).child("FilmOfGroup").child("Ney").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ney.clear();
                try {
                    int count = 0;
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        ney.add(ds.getKey());count += 1;}
                    tv5.setText(String.valueOf(count));

                }catch (Exception e){}

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });












        mbd.child("Group").child(grId).child("FilmOfGroup").child("Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String name = Objects.requireNonNull(snapshot.getValue()).toString();
                    mbd.child("Film").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Film fl = snapshot.getValue(Film.class);
                            try {
                                tv3.setText(fl.name);
                                Picasso.get().load(fl.url).fit()
                                        .centerCrop().into(iv);
                            } catch (NullPointerException e){}


                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }catch (Exception e){
                    iv2.setVisibility(View.GONE);
                    iv3.setVisibility(View.GONE);
                    iv4.setVisibility(View.GONE);
                    tv4.setVisibility(View.GONE);
                    tv5.setVisibility(View.GONE);
                    tv6.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        iv1.setOnClickListener(view -> {
            Intent switcher = new Intent(GroupActivity.this, MainActivity.class);
            startActivity(switcher);
        });
        tv.setText(grId);

        mbd.child("Group").child(grId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(Group.class).name;
                tv1.setText(name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
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
                Intent switcher = new Intent(GroupActivity.this, GroupFilmsActivity.class).putExtra("id", tv.getText());
                startActivity(switcher);

            }
        });

         bt2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 try {
                     mbd.child("Group").child(grId).child("GrFilms").addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                             ArrayList<String> grfl = new ArrayList<>();
                             for (DataSnapshot ds : snapshot.getChildren()) {
                                 grfl.add(ds.getKey());
                             }
                             if (grfl.size() > 0) {
                                 int rd = new Random().nextInt(grfl.size());
                                 mbd.child("Group").child(grId).child("FilmOfGroup").child("Name").setValue(grfl.get(rd));
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
                                 iv2.setVisibility(View.VISIBLE);
                                 iv3.setVisibility(View.VISIBLE);
                                 iv4.setVisibility(View.VISIBLE);
                                 tv4.setVisibility(View.VISIBLE);
                                 tv5.setVisibility(View.VISIBLE);
                                 tv6.setVisibility(View.VISIBLE);
                             } else {
                                 Toast.makeText(GroupActivity.this, "Ой, кажется вы не выбрали фильмы", Toast.LENGTH_LONG).show();
                             }
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError error) {
                         }
                     });

                 }catch (Exception e){}
             }
         });



         iv2.setOnClickListener(view -> {
             DatabaseReference db = mbd.child("Group")
                     .child(grId).child("FilmOfGroup");
             if (liked.contains(ma.getCurrentUser().getUid())){
                 db.child("Likes").child(ma.getCurrentUser().getUid()).removeValue();
             }else if (dis.contains(ma.getCurrentUser().getUid())) {
                 db.child("Dis").child(ma.getCurrentUser().getUid()).removeValue();
                 db.child("Likes").child(ma.getCurrentUser().getUid()).setValue(ma.getCurrentUser().getUid());

             }else if (ney.contains(ma.getCurrentUser().getUid())) {
                 db.child("Ney").child(ma.getCurrentUser().getUid()).removeValue();
                 db.child("Likes").child(ma.getCurrentUser().getUid()).setValue(ma.getCurrentUser().getUid());

            }else {
                 db.child("Likes").child(ma.getCurrentUser().getUid()).setValue(ma.getCurrentUser().getUid());
             }
             db.child("Likes").addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     liked.clear();
                     try {
                         int count = 0;
                         for(DataSnapshot ds: dataSnapshot.getChildren()){
                             liked.add(ds.getKey());count += 1;}
                         tv4.setText(String.valueOf(count));

                     }catch (Exception e){}

                 }
                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {}
             });
             db.child("Dis").addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     dis.clear();
                     try {
                         int count = 0;
                         for(DataSnapshot ds: dataSnapshot.getChildren()){
                             dis.add(ds.getKey());count += 1;}
                         tv6.setText(String.valueOf(count));

                     }catch (Exception e){}

                 }
                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {}
             });
             db.child("Ney").addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     ney.clear();
                     try {
                         int count = 0;
                         for(DataSnapshot ds: dataSnapshot.getChildren()){
                             ney.add(ds.getKey());count += 1;}
                         tv5.setText(String.valueOf(count));

                     }catch (Exception e){}

                 }
                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {}
             });

         });



        iv3.setOnClickListener(view -> {
            DatabaseReference db = mbd.child("Group")
                    .child(grId).child("FilmOfGroup");
            if (dis.contains(ma.getCurrentUser().getUid())){
                db.child("Dis").child(ma.getCurrentUser().getUid()).removeValue();
            }else if (liked.contains(ma.getCurrentUser().getUid())) {
                db.child("Likes").child(ma.getCurrentUser().getUid()).removeValue();
                db.child("Dis").child(ma.getCurrentUser().getUid()).setValue(ma.getCurrentUser().getUid());

            }else if (ney.contains(ma.getCurrentUser().getUid())) {
                db.child("Ney").child(ma.getCurrentUser().getUid()).removeValue();
                db.child("Dis").child(ma.getCurrentUser().getUid()).setValue(ma.getCurrentUser().getUid());

            }else {
                db.child("Dis").child(ma.getCurrentUser().getUid()).setValue(ma.getCurrentUser().getUid());
            }
            db.child("Likes").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    liked.clear();
                    try {
                        int count = 0;
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            liked.add(ds.getKey());count += 1;}
                        tv4.setText(String.valueOf(count));

                    }catch (Exception e){}

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
            db.child("Dis").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dis.clear();
                    try {
                        int count = 0;
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            dis.add(ds.getKey());count += 1;}
                        tv6.setText(String.valueOf(count));

                    }catch (Exception e){}

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
            db.child("Ney").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ney.clear();
                    try {
                        int count = 0;
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            ney.add(ds.getKey());count += 1;}
                        tv5.setText(String.valueOf(count));

                    }catch (Exception e){}

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });

        });


        iv4.setOnClickListener(view -> {
            DatabaseReference db = mbd.child("Group")
                    .child(grId).child("FilmOfGroup");
            if (ney.contains(ma.getCurrentUser().getUid())){
                db.child("Ney").child(ma.getCurrentUser().getUid()).removeValue();
            }else if (dis.contains(ma.getCurrentUser().getUid())) {
                db.child("Dis").child(ma.getCurrentUser().getUid()).removeValue();
                db.child("Ney").child(ma.getCurrentUser().getUid()).setValue(ma.getCurrentUser().getUid());

            }else if (liked.contains(ma.getCurrentUser().getUid())) {
                db.child("Likes").child(ma.getCurrentUser().getUid()).removeValue();
                db.child("Ney").child(ma.getCurrentUser().getUid()).setValue(ma.getCurrentUser().getUid());

            }else {
                db.child("Ney").child(ma.getCurrentUser().getUid()).setValue(ma.getCurrentUser().getUid());
            }
            db.child("Likes").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    liked.clear();
                    try {
                        int count = 0;
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            liked.add(ds.getKey());count += 1;}
                        tv4.setText(String.valueOf(count));

                    }catch (Exception e){}

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
            db.child("Dis").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dis.clear();
                    try {
                        int count = 0;
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            dis.add(ds.getKey());count += 1;}
                        tv6.setText(String.valueOf(count));

                    }catch (Exception e){}

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
            db.child("Ney").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ney.clear();
                    try {
                        int count = 0;
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            ney.add(ds.getKey());count += 1;}
                        tv5.setText(String.valueOf(count));

                    }catch (Exception e){}

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });

        });

    }
}