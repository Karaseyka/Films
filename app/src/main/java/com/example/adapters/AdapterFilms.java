package com.example.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.films.ui.main.forFilm.Film;
import com.example.films.ui.main.forGroup.GroupActivity;
import com.example.films.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class AdapterFilms  extends RecyclerView.Adapter<AdapterFilms.FilmsHolder> {
    Context context;
    ArrayList<Film> film;
    String id;
    int act;


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
        View view = layoutInflater.inflate(R.layout.list_item_films, parent, false);
        return new FilmsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFilms.FilmsHolder holder, int position) {
        Film fl = film.get(position);
        holder.tv.setText(fl.name);
        holder.tv1.setText(fl.id);
        Picasso.get().load(fl.url).fit()
                .centerCrop().into(holder.iv);

    }
    @Override
    public int getItemCount() {
        return film.size();
    }

    public class FilmsHolder extends RecyclerView.ViewHolder {
        DatabaseReference mdb;
        FirebaseAuth ma;
        TextView tv;
        TextView tv1;
        ImageView iv;
        Context context = itemView.getContext();

        public FilmsHolder(@NonNull View itemView) {
            super(itemView);
            switch (AdapterFilms.this.act){
                case 1:
                    forAdd(itemView);
                    break;
                case 2:
                    forList(itemView);
                    break;
            }


        }
        public void forAdd(View itemView){
            tv = itemView.findViewById(R.id.textView14);
            tv1 = itemView.findViewById(R.id.id);
            iv = itemView.findViewById(R.id.imageView3);
            String id = AdapterFilms.this.id;

            mdb = FirebaseDatabase.getInstance().getReference();

            itemView.setOnClickListener(new View.OnClickListener() {
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
                            String c = tv1.getText().toString();
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
        public void forList(View itemView){
            tv = itemView.findViewById(R.id.textView14);
            tv1 = itemView.findViewById(R.id.id);
            iv = itemView.findViewById(R.id.imageView3);

        }
    }

}

