package com.example.films.ui.main.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.example.UserInfo;
import com.example.films.R;
import com.example.films.databinding.ActivityMainBinding;
import com.instabug.library.Instabug;
import com.instabug.library.invocation.InstabugInvocationEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private UserInfo userInfo;
    FragmentManager fragmentManager = getSupportFragmentManager();
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        new Instabug.Builder(this.getApplication(), "2a06840a480c1624e4bdbc6d6a888488")
                .setInvocationEvents(InstabugInvocationEvent.SHAKE, InstabugInvocationEvent.SCREENSHOT)
                .build();
        setContentView(binding.getRoot());
        Toolbar tb = (Toolbar) binding.myToolbar;
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tb.setLogo(R.drawable.logo);
        tb.setTitle("");
        tb.setSubtitle("");

        userInfo = new UserInfo();

        //navController = Navigation.findNavController(this, R.id.fragment_container);


       /* FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, ProfileFragment.getInstance(), "prof");
        fragmentTransaction.commit();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        navController = Navigation.findNavController(this, R.id.fragment_container);



        switch (item.getItemId()) {
            case R.id.profile:
                if ( ! navController.popBackStack(R.id.ProfileFragment, false)) {
                    //navController.navigate(R.id.ProfileFragment);
                    navController.navigate(R.id.ProfileFragment, null, new NavOptions.Builder().setLaunchSingleTop(true).build());
                }
                navController.popBackStack(R.id.ProfileFragment, false);

                return true;

            case R.id.search:
                if ( ! navController.popBackStack(R.id.SearchFragment, false)) {
                    //navController.navigate(R.id.ProfileFragment);
                    navController.navigate(R.id.SearchFragment, null, new NavOptions.Builder().setLaunchSingleTop(true).build());
                }
                navController.popBackStack(R.id.SearchFragment, false);



                return true;
            case R.id.groups:
                if ( ! navController.popBackStack(R.id.GroupsFragment, false)) {
                    //navController.navigate(R.id.ProfileFragment);
                    navController.navigate(R.id.GroupsFragment, null, new NavOptions.Builder().setLaunchSingleTop(true).build());
                }
                navController.popBackStack(R.id.GroupsFragment, false);



                return true;
            case R.id.exit:
                return userInfo.signOut(MainActivity.this, (Activity) this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*private void replaceFragment(Fragment fragment, String mess) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fg = fragmentManager.findFragmentByTag(mess);
        if(fg == null) {
            fragmentTransaction.add(R.id.fragment_container, fragment, mess);
            Log.d("sgafsftdsdhjdhtghjndkvk", "if");
        }else{
            fragmentTransaction.remove(fg);
            fragmentTransaction.add(R.id.fragment_container, fg, mess);
            Log.d("jbasijgblkasbg;au", "else");
        }
        fragmentTransaction.commit();
    }*/

}