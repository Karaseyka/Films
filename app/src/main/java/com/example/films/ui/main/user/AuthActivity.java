package com.example.films.ui.main.user;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.films.ui.main.fragments.MainActivity;
import com.example.films.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AuthActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        sp = AuthActivity.this.getSharedPreferences("Auth_info", Context.MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        Button bt = (Button) findViewById(R.id.button);
        TextView tw = (TextView) findViewById(R.id.textView2);
        EditText et1 = (EditText) findViewById(R.id.editText);
        EditText et2 = (EditText) findViewById(R.id.editTextTextPassword);
        if (!sp.getString("auth_info", "404").equals("404")){
            Intent switcher = new Intent(AuthActivity.this, MainActivity.class);
            startActivity(switcher);
            finish();
        } else {
            Log.d("vbgnm", sp.getString("auth_info", "404"));
        }


        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switcher1 = new Intent(AuthActivity.this, RegistrationActivity.class);
                startActivity(switcher1);
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et1.getText().toString().length() > 0 && et2.getText().toString().length() > 0) {
                    mAuth.signOut();
                    mAuth.signInWithEmailAndPassword(et1.getText().toString(), et2.getText().toString())
                            .addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        sp = AuthActivity.this.getSharedPreferences("Auth_info", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor ed = sp.edit();
                                        ed.putString("auth_info", "True");
                                        ed.apply();
                                        Intent switcher = new Intent(AuthActivity.this, MainActivity.class);
                                        startActivity(switcher);
                                        finish();


                                    } else {

                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(AuthActivity.this, "Ошибка в авторизации",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
            }
        });
    }


}