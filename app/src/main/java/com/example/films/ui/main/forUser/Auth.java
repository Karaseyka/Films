package com.example.films.ui.main.forUser;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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


public class Auth extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mAuth = FirebaseAuth.getInstance();
        Button bt = (Button) findViewById(R.id.button);
        TextView tw = (TextView) findViewById(R.id.textView2);
        EditText et1 = (EditText) findViewById(R.id.editText);
        EditText et2 = (EditText) findViewById(R.id.editTextTextPassword);

        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switcher1 = new Intent(Auth.this, Registration.class);
                startActivity(switcher1);
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et1.getText().toString().length() > 0 && et2.getText().toString().length() > 0) {
                    mAuth.signOut();
                    mAuth.signInWithEmailAndPassword(et1.getText().toString(), et2.getText().toString())
                            .addOnCompleteListener(Auth.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent switcher = new Intent(Auth.this, MainActivity.class);
                                        startActivity(switcher);


                                    } else {

                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Auth.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
            }
        });
    }


}