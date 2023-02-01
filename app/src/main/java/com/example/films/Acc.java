package com.example.films;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Acc extends Fragment {
    private DatabaseReference mdb;
    private FirebaseAuth ma;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.acc, container, false);
    }
    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        ma = FirebaseAuth.getInstance();
        mdb = FirebaseDatabase.getInstance().getReference();
        EditText et = (EditText) v.findViewById(R.id.textView9);
        EditText et1 = (EditText) v.findViewById(R.id.textView7);
        EditText et2 = (EditText) v.findViewById(R.id.textView11);
        Button bt = (Button) v.findViewById(R.id.button3);

        mdb.child("Users").orderByChild(ma.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.child(ma.getCurrentUser().getUid()).getValue(User.class);
                assert user != null;
                et.setText(user.name);
                et1.setText(user.email);
                et2.setText(user.reg_date);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mdb.child("Users").child(ma.getCurrentUser().getUid()).child("name").setValue(et.getText().toString());
                        mdb.child("Users").child(ma.getCurrentUser().getUid()).child("email").setValue(et1.getText().toString());
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        assert user != null;
                        user.updateEmail(et1.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(view.getContext(), "Успешно", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}