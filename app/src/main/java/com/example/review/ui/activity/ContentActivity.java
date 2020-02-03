package com.example.review.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.review.R;
import com.example.review.ui.fragment.ContentBaseFragment;

public class ContentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);


        Intent intent = getIntent();
        String fullName = intent.getStringExtra("fullName");

        Toolbar toolbar = findViewById(R.id.contentToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(fullName);

        TextView star = findViewById(R.id.contentStarText);
        TextView fork = findViewById(R.id.contentForkText);
        TextView watch = findViewById(R.id.contentWatchText);
        TextView license = findViewById(R.id.contentLicenseText);

        star.setText(String.format("%d",intent.getIntExtra("star",0)));
        fork.setText(String.format("%d", intent.getIntExtra("fork",0)));
        watch.setText(String.format("%d",intent.getIntExtra("watch",0)));
        license.setText(intent.getStringExtra("license"));

        Fragment fragment = ContentBaseFragment.newInstance(fullName);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contentFrameLayout, fragment)
                .commit();

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_slide_in,R.anim.activity_slide_out);
    }

}