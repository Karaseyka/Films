package com.example.films.ui.main.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.films.ui.main.forGroup.Group;
import com.example.films.R;
import com.example.adapters.Adapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
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

        mdb.child("Users").child(ma.getCurrentUser().getUid()).child("Group").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Group> list = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    String gr1 = postSnapshot.getValue().toString();
                    assert gr1 != null;
                    mdb1.child("Group").child(gr1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot bSnapshot) {

                            Group gr = bSnapshot.getValue(Group.class);
                            RecyclerView rv = (RecyclerView) v.findViewById(R.id.recyclerview);

                            list.add(gr);

                            Adapter ad = new Adapter((Context) getActivity(), list);
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


    }
}