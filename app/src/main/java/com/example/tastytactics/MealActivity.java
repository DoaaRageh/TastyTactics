package com.example.tastytactics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
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
import com.example.tastytactics.plan.view.PlanFragment;
import com.example.tastytactics.search.view.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MealActivity extends AppCompatActivity {

    FragmentTransaction trns;
    private BroadcastReceiver networkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meal);
        FragmentManager mgr = getSupportFragmentManager();
        trns = mgr.beginTransaction();
        if (savedInstanceState == null) {
            if (isNetworkAvailable(MealActivity.this)) {
                showFragment(new HomeFragment());
            } else {
                showFragment(new NoNetworkFragment());
            }
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                loadSelectedBottomNavigationFragment(item.getItemId());
                return true;
            }
        });


        networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (isNetworkAvailable(context)) {
                    loadSelectedBottomNavigationFragment(bottomNav.getSelectedItemId());  // Load selected item
                } else {
                    if(bottomNav.getSelectedItemId() == R.id.item3 || bottomNav.getSelectedItemId() == R.id.item4) {
                        loadSelectedBottomNavigationFragment(bottomNav.getSelectedItemId());  // Load selected item
                    }
                    else {
                        showFragment(new NoNetworkFragment());
                    }
                }
            }
        };

        // Register the receiver to listen for network changes
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);

    }

    // Helper method to load the fragment based on the selected bottom navigation item
    private void loadSelectedBottomNavigationFragment(int itemId) {
        Fragment selectedFragment = null;
        if (itemId == R.id.item1) {
            if (isNetworkAvailable(MealActivity.this)) {
                selectedFragment = new HomeFragment();
            } else {
                selectedFragment = new NoNetworkFragment();
            }
        } else if (itemId == R.id.item2) {
            if (isNetworkAvailable(MealActivity.this)) {
                selectedFragment = new SearchFragment();
            } else {
                selectedFragment = new NoNetworkFragment();
            }
        } else if (itemId == R.id.item3) {
            selectedFragment = new PlanFragment();
        } else if (itemId == R.id.item4) {
            selectedFragment = new FavMealsFragment();
        }
        showFragment(selectedFragment);
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        } else {
            // For older devices
            return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
        }
    }
}