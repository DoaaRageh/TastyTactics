package com.example.tastytactics;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tastytactics.home.view.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MealActivity extends AppCompatActivity {

    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meal);
        FragmentManager mgr = getSupportFragmentManager();
        FragmentTransaction trns = mgr.beginTransaction();
        if (savedInstanceState == null) {
            homeFragment = new HomeFragment();
            trns.replace(R.id.fragmentContainer, homeFragment, "HomeFragment");
            trns.commit();
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.item1) {
                    homeFragment = (HomeFragment) mgr.findFragmentByTag("HomeFragment");
                    return true;
                } else if (id == R.id.item2) {
                    return true;
                } else if (id == R.id.item3) {
                    return true;
                } else if (id == R.id.item4) {
                    return true;
                } else {
                    return false;
                }
            }
        });

    }
}