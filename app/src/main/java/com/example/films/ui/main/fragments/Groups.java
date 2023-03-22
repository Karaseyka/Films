package com.example.films.ui.main.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.DialogCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.films.ui.main.forGroup.Group;
import com.example.films.R;
import com.example.adapters.Adapter;
import com.example.films.ui.main.forUser.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Groups extends Fragment {
    private DatabaseReference mdb;
    private DatabaseReference mdb1;
    private FirebaseAuth ma;
    private FirebaseRecyclerAdapter rA;

    ArrayList<Group> list = new ArrayList<>();
    Adapter ad = new Adapter((Context) getActivity(), list);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_groups, container, false);
    }
    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        ma = FirebaseAuth.getInstance();
        mdb = FirebaseDatabase.getInstance().getReference();
        mdb1 = FirebaseDatabase.getInstance().getReference();
        RecyclerView rv = (RecyclerView) v.findViewById(R.id.recyclerview);

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
                            rv.setAdapter(ad);
                            rv.setLayoutManager(new LinearLayoutManager((Context) getActivity()));
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
        FloatingActionButton fb = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        fb.bringToFront();
        fb.setOnClickListener(view -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder((Context) getActivity());
            builder1.setMessage("Создайте новую группу");
            builder1.setCancelable(true);
            final EditText input = new EditText((Context) getActivity());
            builder1.setView(input);
            builder1.setPositiveButton(
                    "Сохранить",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FirebaseUser user = ma.getCurrentUser();
                            String gr = "   " + input.getText().toString();
                            String a = mdb.child("Users").child(user.getUid()).child("Group").push().getKey();
                            Group g = new Group(gr, a);
                            mdb.child("Users").child(user.getUid()).child("Group").child(a).setValue(a);
                            mdb.child("Group").child(a).setValue(g);
                            list.add(new Group(gr, a));
                            ad.notifyDataSetChanged();
                            //Log.d("dffffffffffffffffffffffff", input.getText().toString());
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    "Назад",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });



            AlertDialog alert11 = builder1.create();
            alert11.show();

        });


    }
}