package com.example.tastytactics;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tastytactics.favmeals.view.FavMealsFragment;
import com.example.tastytactics.home.view.HomeFragment;
import com.example.tastytactics.search.view.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MealActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private FavMealsFragment favMealsFragment;
    private Fragment fragment;
    FragmentTransaction trns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meal);
        FragmentManager mgr = getSupportFragmentManager();
        trns = mgr.beginTransaction();
        if (savedInstanceState == null) {
            fragment = new HomeFragment();
            trns.replace(R.id.fragmentContainer, fragment, "HomeFragment");
            trns.commit();
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.item1) {
                    trns = mgr.beginTransaction();
                    homeFragment = new HomeFragment();
                    trns.replace(R.id.fragmentContainer, homeFragment, "SearchFragment");
                    trns.commit();
                    return true;
                } else if (id == R.id.item2) {
                    trns = mgr.beginTransaction();
                    searchFragment = new SearchFragment();
                    trns.replace(R.id.fragmentContainer, searchFragment, "SearchFragment");
                    trns.commit();
                    return true;
                } else if (id == R.id.item3) {
                    return true;
                } else if (id == R.id.item4) {
                    trns = mgr.beginTransaction();
                    favMealsFragment = new FavMealsFragment();
                    trns.replace(R.id.fragmentContainer, favMealsFragment, "FavFragment");
                    trns.commit();
                    return true;
                } else {
                    return false;
                }
            }
        });

        /*bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.item1) {
                    homeFragment = (HomeFragment) mgr.findFragmentByTag("HomeFragment");
                    return true;
                } else if (id == R.id.item2) {
                    if (searchFragment == null) {
                        trns = mgr.beginTransaction();
                        searchFragment = new SearchFragment();
                        trns.replace(R.id.fragmentContainer, searchFragment, "SearchFragment");
                        trns.commit();
                    }
                    else {
                        searchFragment = (SearchFragment) mgr.findFragmentByTag("SearchFragment");
                    }
                    return true;
                } else if (id == R.id.item3) {
                    return true;
                } else if (id == R.id.item4) {
                    return true;
                } else {
                    return false;
                }
            }
        });*/

    }
}