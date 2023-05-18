package com.example.films.ui.main.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.example.UserInfo;
import com.example.films.R;
import com.example.films.databinding.ActivityMainBinding;

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
        setContentView(binding.getRoot());
        Toolbar tb = (Toolbar) binding.myToolbar;
        setSupportActionBar(tb);
        tb.setLogo(R.drawable.logo);

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
                // User chose the "Settings" item, show the app settings UI...
                // replaceFragment(ProfileFragment.getInstance(), "prof");
                //navController.popBackStack(R.id.profile, false);
                //navController.navigate(R.id.ProfileFragment);

                if ( ! navController.popBackStack(R.id.ProfileFragment, false)) {
                    //navController.navigate(R.id.ProfileFragment);
                    navController.navigate(R.id.ProfileFragment, null, new NavOptions.Builder().setLaunchSingleTop(true).build());
                }
                navController.popBackStack(R.id.ProfileFragment, false);
                return true;

            case R.id.search:
                // User chose the "Favorite" action, mark the current item
                if ( ! navController.popBackStack(R.id.SearchFragment, false)) {
                    //navController.navigate(R.id.ProfileFragment);
                    navController.navigate(R.id.SearchFragment, null, new NavOptions.Builder().setLaunchSingleTop(true).build());
                }
                navController.popBackStack(R.id.SearchFragment, false);                return true;
            case R.id.groups:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                if ( ! navController.popBackStack(R.id.GroupsFragment, false)) {
                    //navController.navigate(R.id.ProfileFragment);
                    navController.navigate(R.id.GroupsFragment, null, new NavOptions.Builder().setLaunchSingleTop(true).build());
                }
                navController.popBackStack(R.id.GroupsFragment, false);                return true;
            case R.id.exit:
                return userInfo.signOut();
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
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