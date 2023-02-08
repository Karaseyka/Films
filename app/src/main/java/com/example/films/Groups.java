package com.example.films;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Groups extends Fragment {
    private DatabaseReference mdb;
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

        mdb.child("Users").child(ma.getCurrentUser().getUid()).child("Group").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> a = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {


                    Group gr1 = postSnapshot.getValue(Group.class);
                    assert gr1 != null;
                    a.add(gr1.name);

                }
                RecyclerView rv = (RecyclerView) v.findViewById(R.id.recyclerview);
                ArrayList<Group> list = new ArrayList<>();

                for(int i = 0; i < a.size(); i ++){
                    Group gr = new Group(a.get(i));
                    list.add(gr);
                }
                Adapter ad = new Adapter((Context) getActivity(), list);
                rv.setAdapter(ad);
                rv.setLayoutManager(new LinearLayoutManager((Context) getActivity()));


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}