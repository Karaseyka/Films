package com.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.films.ui.main.user.AuthActivity;

public class UserInfo {


    public boolean signOut(Context context, Activity activity){

        SharedPreferences sp;
        sp = context.getSharedPreferences("Auth_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("auth_info", "404");
        ed.apply();
        Intent switcher = new Intent(context, AuthActivity.class);
        context.startActivity(switcher);
        activity.finish();
        return true;
    }
}
