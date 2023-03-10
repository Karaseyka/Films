package com.example.films.ui.main.forUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.films.R;
import com.example.films.ui.main.forFilm.Film;
import com.example.films.ui.main.forGroup.Group;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
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
        DatabaseReference mdb1 = FirebaseDatabase.getInstance().getReference();
//        String a = mdb1.child("Film").push().getKey();
//        mdb1.child("Film").child(a).setValue(new Film("Время", "https://firebasestorage.googleapis.com/v0/b/films-5a8bb.appspot.com/o/1629795-2124006.jpeg?alt=media&token=967f17a7-5352-499e-9b91-23d095baf1da", "Aboba", a));
//        a = mdb1.child("Film").push().getKey();
//        mdb1.child("Film").child(a).setValue(new Film("Во всё тяжкое", "https://firebasestorage.googleapis.com/v0/b/films-5a8bb.appspot.com/o/33340317-1124423.jpg?alt=media&token=00d18a74-97bc-4f1d-9eef-99c4ce61b616", "Aboba", a));
//        a = mdb1.child("Film").push().getKey();
//        mdb1.child("Film").child(a).setValue(new Film("1+1", "https://firebasestorage.googleapis.com/v0/b/films-5a8bb.appspot.com/o/RKWiodWYDEU.jpg?alt=media&token=96cffaaf-0cb1-438c-977f-15096242acbd", "Aboba", a));
//        a = mdb1.child("Film").push().getKey();
//        mdb1.child("Film").child(a).setValue(new Film("Титаник", "https://firebasestorage.googleapis.com/v0/b/films-5a8bb.appspot.com/o/c7e53efbe083c67fb76bb137928f1815.jpg?alt=media&token=b28a3f6a-7fad-4dd4-8654-9467348ebc8d", "Aboba", a));
//        a = mdb1.child("Film").push().getKey();
//        mdb1.child("Film").child(a).setValue(new Film("Операция Ы", "https://firebasestorage.googleapis.com/v0/b/films-5a8bb.appspot.com/o/orig.jfif?alt=media&token=c25e55e9-c6af-4bc9-abde-73a11cd1926c", "Aboba", a));
        mAuth = FirebaseAuth.getInstance();
        Button bt = (Button) findViewById(R.id.button2);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = et2.getText().toString();
                String b = et3.getText().toString();
                String c = et.getText().toString();
                String d = et1.getText().toString();
                if (a.length() >= 8 && b.toString().length() >= 8 && a.equals(b) && c.length() > 0 && d.length() > 0) {
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
                                        String gr = "   MyFirstGroup";
                                        User u = new User(mdb.getKey(), c, d,"0", mAuth.getCurrentUser().toString(), f.format(date).toString());
                                        mdb.child("Users").child(user.getUid()).setValue(u);
                                        String a = mdb.child("Users").child(user.getUid()).child("Group").push().getKey();
                                        Group g = new Group(gr, a);
                                        mdb.child("Users").child(user.getUid()).child("Group").child(a).setValue(a);
                                        mdb.child("Group").child(a).setValue(g);
                                        Intent switcher = new Intent(Registration.this, Auth.class);
                                        startActivity(switcher);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Registration.this, "Ошибка регистрации",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }

}

