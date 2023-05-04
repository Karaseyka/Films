package com.example.films.ui.main.group;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.films.ui.main.fragments.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Data {
    FirebaseAuth ma = FirebaseAuth.getInstance();

    DatabaseReference mbd = FirebaseDatabase.getInstance().getReference();

    public void p(String grId){
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
                }catch (Exception e){
                    mbd.child("Group").child(grId).removeValue();
                    mbd.child("Users").child(ma.getCurrentUser().getUid()).child("Group").child(grId).removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
