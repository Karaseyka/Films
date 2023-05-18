package com.example.films.ui.main.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.films.R;
import com.example.films.ui.main.film.Film;
import com.example.films.ui.main.group.Group;
import com.example.web.ApiService;
import com.example.web.Docs;
import com.example.web.Kinopoisk;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {
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
                            .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegistrationActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                                        DatabaseReference mdb = FirebaseDatabase.getInstance().getReference();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Date date = new Date();
                                        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
                                        String gr = "   MyFirstGroup";
                                        User u = new User(user.getUid(), c, d,"0", mAuth.getCurrentUser().toString(), f.format(date).toString());
                                        mdb.child("Users").child(user.getUid()).setValue(u);
                                        String a = mdb.child("Users").child(user.getUid()).child("Group").push().getKey();
                                        Group g = new Group(gr, a);
                                        mdb.child("Users").child(user.getUid()).child("Group").child(a).setValue(a);
                                        mdb.child("Group").child(a).setValue(g);
                                        Intent switcher = new Intent(RegistrationActivity.this, AuthActivity.class);
                                        startActivity(switcher);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegistrationActivity.this, "Ошибка регистрации",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


    }

}

