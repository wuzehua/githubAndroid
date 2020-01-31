package com.example.review.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.review.R;
import com.example.review.api.service.GithubService;
import com.example.review.fragment.BaseFragment;
import com.example.review.fragment.UserInfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;




public class MainActivity extends ServiceActivity {

    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final UserInfoFragment userInfoFragment = new UserInfoFragment(getApiService());
        final BaseFragment baseFragment = new BaseFragment(getApiService());

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        mCurrentFragment = baseFragment;

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainFragmentLayout, baseFragment)
                .commit();

        BottomNavigationView bottomNavigate = findViewById(R.id.bottomNavigate);
        bottomNavigate.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.homeNavigate:{
                        switchFragment(baseFragment);
                        break;
                    }

                    case R.id.meNavigate:{
                        switchFragment(userInfoFragment);
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