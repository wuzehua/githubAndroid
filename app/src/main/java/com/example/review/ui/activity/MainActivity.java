package com.example.review.ui.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.review.R;
import com.example.review.ui.fragment.BaseFragment;
import com.example.review.ui.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;




public class MainActivity extends AppCompatActivity {

    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView logout = findViewById(R.id.logoutButton);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Logout");
                builder.setMessage("Are you sure to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        SharedPreferences preferences = getSharedPreferences("loginStat", Context.MODE_PRIVATE);
                        preferences.edit().putBoolean("login",false).apply();
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.create().show();
            }
        });

        final Fragment homeFragment = HomeFragment.newInstance();
        final Fragment baseFragment = BaseFragment.newInstance();

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        mCurrentFragment = homeFragment;

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainFragmentLayout, mCurrentFragment)
                .commit();

        BottomNavigationView bottomNavigate = findViewById(R.id.bottomNavigate);
        bottomNavigate.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.homeNavigate:{
                        switchFragment(homeFragment);
                        break;
                    }

                    case R.id.meNavigate:{
                        switchFragment(baseFragment);
                        break;
                    }
                }
                return true;
            }
        });


    }

    private void switchFragment(Fragment fragment){
        if(mCurrentFragment != fragment){
            if(fragment.isAdded()){
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mCurrentFragment)
                        .show(fragment)
                        .commit();
            }else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mCurrentFragment)
                        .add(R.id.mainFragmentLayout, fragment)
                        .commit();
            }

            mCurrentFragment = fragment;
        }
    }




}