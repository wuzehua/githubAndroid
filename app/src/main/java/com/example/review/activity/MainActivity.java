package com.example.review.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.review.R;
import com.example.review.api.service.GithubService;
import com.example.review.fragment.LoginFragment;
import com.example.review.fragment.RecyclerFragment;


public class MainActivity extends AppCompatActivity {

    private Fragment mCurrentFragment;
    private Retrofit mRetrofit;
    private GithubService mApiGithubService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LoginFragment loginFragment = new LoginFragment();
        final RecyclerFragment recyclerFragment = new RecyclerFragment(getApiService());

        mCurrentFragment = recyclerFragment;

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainFragmentLayout, recyclerFragment)
                .commit();

        RadioGroup tabs = findViewById(R.id.bottomNavigate);
        tabs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.recyclerTab:{
                        switchFragment(recyclerFragment);
                        break;
                    }
                    case R.id.loginTab:{
                        switchFragment(loginFragment);
                        break;
                    }
                }
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

    private GithubService getApiService(){
        if(mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(GithubService.GITHUB_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        if(mApiGithubService == null){
            mApiGithubService = mRetrofit.create(GithubService.class);
        }

        return mApiGithubService;
    }



}