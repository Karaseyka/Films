package com.example.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.films.R;
import com.example.films.ui.main.forGroup.Group;
import com.example.films.ui.main.forGroup.GroupActivity;
import com.example.films.ui.main.forUser.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.UserHolder> {
    Context context;
    ArrayList<User> user;
    ArrayList<Group> list = new ArrayList<>();
    AdapterGrDialog ad;

    public AdapterUser(Context context, ArrayList<User> user){
        this.context = context;
        this.user = user;

    }


    @NonNull
    @Override
    public AdapterUser.UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_item, parent, false);
        return new AdapterUser.UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUser.UserHolder holder, int position) {
        User us = user.get(position);
        holder.tv.setText(us.name);
        holder.tv1.setText(us.id);
        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("kdjhffhjkdkdkgkdkh", us.id);
                ad = new AdapterGrDialog(context, list, us);
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                mBuilder.setTitle("Location Available!");
                LayoutInflater inflater = LayoutInflater.from(context);
                View convertView = inflater.inflate(R.layout.activity_films_list, null);
                DatabaseReference mdb;
                DatabaseReference mdb1;
                FirebaseAuth ma;
                FirebaseRecyclerAdapter rA;

                RecyclerView l = convertView.findViewById(R.id.FilmsList);

                l.setLayoutManager(new LinearLayoutManager(context));
                ma = FirebaseAuth.getInstance();
                mdb = FirebaseDatabase.getInstance().getReference();
                mdb1 = FirebaseDatabase.getInstance().getReference();


                mdb.child("Users").child(ma.getCurrentUser().getUid()).child("Group").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            String gr1 = postSnapshot.getValue().toString();
                            assert gr1 != null;
                            mdb1.child("Group").child(gr1).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot bSnapshot) {

                                    Group gr = bSnapshot.getValue(Group.class);

                                    list.add(gr);
                                    l.setAdapter(ad);
                                    l.setLayoutManager(new LinearLayoutManager(context));
                                    ad.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    throw databaseError.toException(); // never ignore errors
                                }
                            });

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                mBuilder.setView(convertView); // setView

                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
        //holder.tv1.setText(us.id);

    }

    @Override
    public int getItemCount() {
        return this.user.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class UserHolder extends RecyclerView.ViewHolder {

        TextView tv;
        TextView tv1;
        Button bt;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.username);
            tv1 = itemView.findViewById(R.id.userid);
            bt = itemView.findViewById(R.id.adduser);
            Context context = itemView.getContext();

        }

    }

}

