package com.example.films.ui.main.fragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.example.films.R;
import com.example.films.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar tb = (Toolbar) binding.myToolbar;
        setSupportActionBar(tb);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                // User chose the "Settings" item, show the app settings UI...
                replaceFragment(new AccFragment());
                return true;

            case R.id.search:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                replaceFragment(new SearchFragment());
                return true;
            case R.id.groups:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                replaceFragment(new GroupsFragment());
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}