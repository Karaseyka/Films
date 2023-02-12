package com.example.films;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterFilms  extends RecyclerView.Adapter<AdapterFilms.FilmsHolder> {
    Context context;
    ArrayList<Film> film;

    public AdapterFilms(Context context, ArrayList<Film> film){
        this.context = context;
        this.film = film;
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
        Picasso.get().load(fl.url).resize(200, 400).into(holder.iv);

    }
    @Override
    public int getItemCount() {
        return film.size();
    }

    public static class FilmsHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;

        public FilmsHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.textView14);
            iv = itemView.findViewById(R.id.imageView3);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent myintent = new Intent(context, GroupActivity.class).putExtra("id", tv1.getText());
//                    context.startActivity(myintent);
//                }
//            });
        }
    }
}
