package com.example.review.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.review.R;
import com.example.review.api.service.GithubService;
import com.example.review.fragment.FilesFragment;

public class ContentActivity extends AppCompatActivity {


    private Retrofit mRetrofit;
    private GithubService mApiGithubService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.statBarColor, getTheme()));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        Intent intent = getIntent();
        String fullName = intent.getStringExtra("fullName");

        Toolbar toolbar = findViewById(R.id.contentToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(fullName);




        Fragment fragment = new FilesFragment(getApiService(), fullName);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contentFrameLayout, fragment)
                .commit();

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