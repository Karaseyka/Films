package com.example.films.ui.main.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ChoiceActivity;
import com.example.films.R;
import com.example.films.ui.main.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {
    private DatabaseReference mdb;
    private FirebaseAuth ma;
    private static ProfileFragment instance = null;
    private boolean uploaded = false;

    public static ProfileFragment getInstance() {
        if (instance == null) {
            instance = new ProfileFragment();
        }
        return instance;
    }

    /*@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        ma = FirebaseAuth.getInstance();
        mdb = FirebaseDatabase.getInstance().getReference();
        EditText et = (EditText) v.findViewById(R.id.textView9);
        EditText et1 = (EditText) v.findViewById(R.id.textView7);
        EditText et2 = (EditText) v.findViewById(R.id.textView11);
        Button bt = (Button) v.findViewById(R.id.button3);
        Button bt2 = (Button) v.findViewById(R.id.toChoise);
        ProgressDialog dialog = new ProgressDialog(v.getContext());
        dialog.setMessage("Подождите");
        dialog.setCancelable(true); // todo change
        dialog.setInverseBackgroundForced(false);
        if (!uploaded) {
            dialog.show();
        }


        load(user -> {
            et.setText(user.name);
            et1.setText(user.email);
            et2.setText(user.reg_date);
            dialog.hide();
            uploaded = true;
        });


        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switcher = new Intent(getActivity(), ChoiceActivity.class);
                startActivity(switcher);
            }
        });

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

    @FunctionalInterface
    interface MyAwesomeListener {
        public void loadUser(User user);
    }

    // data layer
    public void load(MyAwesomeListener myAwesomeListener) {

        mdb.child("Users").orderByChild(ma.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.child(ma.getCurrentUser().getUid()).getValue(User.class);
                assert user != null;
                myAwesomeListener.loadUser(user);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}