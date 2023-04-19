package com.example.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.films.ui.main.forFilm.Film;
import com.example.films.ui.main.forGroup.Group;
import com.example.films.ui.main.forGroup.GroupActivity;
import com.example.films.R;
import com.example.films.ui.main.forUser.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class AdapterGrDialog  extends RecyclerView.Adapter<AdapterGrDialog.GrDiologHolder> {
    Context context;
    ArrayList<Group> gr;
    User user;
    Dialog dialog;



    public AdapterGrDialog(Context context, ArrayList<Group> gr, User user, Dialog dialog){
        this.context = context;
        this.user = user;
        this.gr = gr;
        this.dialog = dialog;


    }


    @NonNull
    @Override
    public AdapterGrDialog.GrDiologHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new AdapterGrDialog.GrDiologHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGrDialog.GrDiologHolder holder, int position) {
        Group group = gr.get(position);
        holder.tv.setText(group.name);
        holder.tv1.setText(group.id);


    }
    @Override
    public int getItemCount() {
        return gr.size();
    }

    public class GrDiologHolder extends RecyclerView.ViewHolder {
        DatabaseReference mdb;
        FirebaseAuth ma;
        TextView tv;
        TextView tv1;

        Context context = itemView.getContext();

        public GrDiologHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.name);
            tv1 = itemView.findViewById(R.id.id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mdb = FirebaseDatabase.getInstance().getReference();
                    mdb.child("Group").child(tv1.getText().toString()).child("Users").child(user.id).setValue(user.id);
                    mdb.child("Users").child(user.id).child("Group").child(tv1.getText().toString()).setValue(tv1.getText().toString());
                    dialog.cancel();
                }
            });

            }


        }


}