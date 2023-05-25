package com.example.films.ui.main.user;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
        TextView tw1 = (TextView) findViewById(R.id.textView3);
        EditText et1 = (EditText) findViewById(R.id.editText);
        EditText et2 = (EditText) findViewById(R.id.editTextTextPassword);
        tw1.setOnClickListener(v -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setTitle("Смена пароля");
                    LinearLayout linearLayout=new LinearLayout(this);
                    final EditText emailet= new EditText(this);

                    // write the email using which you registered
                    emailet.setHint("Email");
                    emailet.setMinEms(16);
                    emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    linearLayout.addView(emailet);
                    linearLayout.setPadding(10,10,10,10);
                    builder.setView(linearLayout);

                    // Click on Recover and a email will be sent to your registered email id
                    builder.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String email=emailet.getText().toString().trim();
                            auth.sendPasswordResetEmail(email)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(AuthActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                    });

                    builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
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