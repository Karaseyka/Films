package com.example.films;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Constants;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Registration extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration);
        EditText et = (EditText) findViewById(R.id.editText);
        EditText et1 = (EditText) findViewById(R.id.editTextTextEmailAddress);
        EditText et2 = (EditText) findViewById(R.id.editTextTextPassword2);
        EditText et3 = (EditText) findViewById(R.id.editTextTextPassword3);

        mAuth = FirebaseAuth.getInstance();
        Button bt = (Button) findViewById(R.id.button2);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = et2.getText().toString();
                String b = et3.getText().toString();
                String c = et.getText().toString();
                String d = et1.getText().toString();
                if (et2.getText().toString().length() >= 8 && et3.getText().toString().length() >= 8 && et2.getText().toString().equals(et3.getText().toString())) {
                    mAuth.createUserWithEmailAndPassword(d, a)
                            .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Registration.this, "Успешно", Toast.LENGTH_SHORT).show();
                                        DatabaseReference mdb = FirebaseDatabase.getInstance().getReference();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Date date = new Date();
                                        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
                                        String gr = "MyFirstGroup";
                                        User u = new User(mdb.getKey(), c, d,"0", mAuth.getCurrentUser().toString(), f.format(date).toString());
                                        mdb.child("Users").child(user.getUid()).setValue(u);
                                        String a = mdb.child("Users").child(user.getUid()).child("Group").push().getKey();
                                        Group g = new Group(gr);
                                        mdb.child("Users").child(user.getUid()).child("Group").child(a).setValue(g);
                                        mdb.child("Group").child(a).setValue(g);
                                        Intent switcher = new Intent(Registration.this, Auth.class);
                                        startActivity(switcher);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Registration.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }

}

