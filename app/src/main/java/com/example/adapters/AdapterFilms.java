package com.example.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.films.ui.main.film.Film;
import com.example.films.ui.main.group.GroupActivity;
import com.example.films.R;
import com.example.films.ui.main.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class AdapterFilms  extends RecyclerView.Adapter<AdapterFilms.FilmsHolder> {
    Context context;
    ArrayList<Film> film;
    String id;
    int act;
    DatabaseReference mdb;
    FirebaseAuth ma;


    public AdapterFilms(Context context, ArrayList<Film> film, String id, int act){
        this.context = context;
        this.film = film;
        this.id = id;
        this.act = act;
    }


    @NonNull
    @Override
    public AdapterFilms.FilmsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if (act == 3){
            view = layoutInflater.inflate(R.layout.item_film_choise, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.list_item_films, parent, false);
        }
        return new FilmsHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFilms.FilmsHolder holder, int position) {
        Film fl = film.get(position);
        TextView tv = holder.itemView.findViewById(R.id.textView14);
        ImageView iv = holder.itemView.findViewById(R.id.imageView3);
        switch (act){
            case 1:
                tv.setText(fl.name);
                Picasso.get().load(fl.url).fit()
                        .centerCrop().into(iv);
                forAdd(holder, fl);
                break;
            case 2:
                tv.setText(fl.name);
                Picasso.get().load(fl.url).fit()
                        .centerCrop().into(iv);
                forList(holder, fl);
                break;
            case 3:
                //TextView tv = holder.itemView.findViewById(R.id.name);
                iv = holder.itemView.findViewById(R.id.film);
                Glide.with(context).load(fl.url).centerCrop().into(iv);

        }

    }
    @Override
    public int getItemCount() {
        return film.size();
    }

    public class FilmsHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;
        ImageView iv1;

        public FilmsHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
        public void forAdd(AdapterFilms.FilmsHolder holder, Film fl){
            ImageView iv1 = holder.itemView.findViewById(R.id.imageView7);

            iv1.setVisibility(View.GONE);
            String id = AdapterFilms.this.id;

            mdb = FirebaseDatabase.getInstance().getReference();

            TextView countl = holder.itemView.findViewById(R.id.countl);
            TextView countdl = holder.itemView.findViewById(R.id.countdl);

            mdb.child("Group").child(id).child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot i: snapshot.getChildren()){
                        mdb.child("Users").child(i.getKey()).child("Like").child(fl.id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    countl.setText(Integer.toString(Integer.parseInt(countl.getText().toString()) + 1));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        mdb.child("Users").child(Objects.requireNonNull(i.getKey())).child("Dislike").child(fl.id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    countdl.setText(Integer.toString(Integer.parseInt(countdl.getText().toString()) + 1));
                                }
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




            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //mdb.child("Group").child(AdapterFilms.this.id).child("GrFilms")
                    mdb.child("Group").child(id).child("GrFilms").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList <String> a = new ArrayList<>();
                            for (DataSnapshot i : snapshot.getChildren()){
                                String b = i.getValue().toString();

                                a.add(b);
                            }
                            String c = fl.id;
                            if (Arrays.asList(a).contains(c)){
                                Toast.makeText(context, "Фильм уже добавлен", Toast.LENGTH_SHORT).show();
                            } else{

                                mdb.child("Group").child(id).child("GrFilms").child(c).setValue(c);
                                Intent myintent = new Intent(context, GroupActivity.class).putExtra("id", id);
                                context.startActivity(myintent);
                                Toast.makeText(context, "Успешно", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });

        }
        public void forList(AdapterFilms.FilmsHolder holder, Film fl){
            String id = AdapterFilms.this.id;
            mdb = FirebaseDatabase.getInstance().getReference();
            TextView countl = holder.itemView.findViewById(R.id.countl);
            TextView countdl = holder.itemView.findViewById(R.id.countdl);

            mdb.child("Group").child(id).child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot i: snapshot.getChildren()){
                        mdb.child("Users").child(i.getKey()).child("Like").child(fl.id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    countl.setText(Integer.toString(Integer.parseInt(countl.getText().toString()) + 1));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        mdb.child("Users").child(Objects.requireNonNull(i.getKey())).child("Dislike").child(fl.id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    countdl.setText(Integer.toString(Integer.parseInt(countdl.getText().toString()) + 1));
                                }
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

