package com.example.makingbetterdecisions;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private NavHostFragment homeNavHost, infoNavHost, accountNavHost, useCasesNavHost;
    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        // Grab each NavHost
        homeNavHost = (NavHostFragment) fragmentManager.findFragmentById(R.id.home_fragment_container);
        infoNavHost = (NavHostFragment) fragmentManager.findFragmentById(R.id.info_fragment_container);
        useCasesNavHost = (NavHostFragment) fragmentManager.findFragmentById(R.id.usecases_fragment_container);
        accountNavHost = (NavHostFragment) fragmentManager.findFragmentById(R.id.account_fragment_container);

        activeFragment = homeNavHost; // start with home

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId(); // get the int ID from menu

            if (id == R.id.navigation_home) {
                // show the home fragment
                showFragment(homeNavHost);
            } else if (id == R.id.navigation_info) {
                // show the search fragment
                showFragment(infoNavHost);
            } else if (id == R.id.navigation_usecases) {
                // show the search fragment
                showFragment(useCasesNavHost);
            } else if (id == R.id.navigation_account) {
                // show the account fragment
                showFragment(accountNavHost);
            }

            return true; // indicate the click was handled
        });
    }

//    private void showFragment(Fragment fragment) {
//        fragmentManager.beginTransaction()
//                .hide(activeFragment)
//                .show(fragment)
//                .commit();
//        activeFragment = fragment;
//    }
private void showFragment(Fragment fragment) {
    FragmentTransaction ft = fragmentManager.beginTransaction();

    // hide all fragments that exist
    if (homeNavHost != null) ft.hide(homeNavHost);
    if (infoNavHost != null) ft.hide(infoNavHost);
    if (useCasesNavHost != null) ft.hide(useCasesNavHost);
    if (accountNavHost != null) ft.hide(accountNavHost);

    // show the selected fragment
    ft.show(fragment);

    ft.commit();

    activeFragment = fragment;
}
    private void hideFragment(Fragment fragment) {
        if (fragment != null && fragment.getView() != null) {
            fragment.getView().setVisibility(View.GONE);
        }
    }

    public void onBackPressedDispatcher() {
        // Let the current visible NavHost handle its own back stack
        NavController currentController =
                ((NavHostFragment) activeFragment).getNavController();

        if (!currentController.popBackStack()) {
            super.onBackPressed();
        }
    }
}

//package com.example.makingbetterdecisions;
//
//import android.os.Bundle;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//import androidx.navigation.fragment.NavHostFragment;
//import androidx.navigation.ui.NavigationUI;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        NavHostFragment navHostFragment =
//                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//        NavController navController = navHostFragment.getNavController();
//
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//}