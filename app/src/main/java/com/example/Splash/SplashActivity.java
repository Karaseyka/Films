package com.example.Splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.window.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import com.example.films.R;
import com.example.films.ui.main.fragments.MainActivity;
import com.example.films.ui.main.user.AuthActivity;
import com.instabug.library.Instabug;
import com.instabug.library.invocation.InstabugInvocationEvent;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        ImageView iv = (ImageView) findViewById(R.id.splashiv);
        sp = SplashActivity.this.getSharedPreferences("Auth_info", Context.MODE_PRIVATE);
        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.visibility);
        iv.setAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {

                if (!sp.getString("auth_info", "404").equals("404")){
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashActivity.this, AuthActivity.class);
                    Log.d("vbgnm", sp.getString("auth_info", "404"));
                    startActivity(i);
                }
                finish(); }
        }, 3000);


    }
}
