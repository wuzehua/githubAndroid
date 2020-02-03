package com.example.review.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.review.R;
import com.example.review.ui.fragment.FragmentPageAdapter;
import com.example.review.ui.fragment.RepoSearchResultFragment;
import com.example.review.ui.fragment.SearchResultFragment;
import com.example.review.ui.fragment.UserSearchResultFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);


        ArrayList<String> titles = new ArrayList<>();
        Collections.addAll(titles,"Repo","User");
        final ArrayList<SearchResultFragment> fragments = new ArrayList<>();
        fragments.add(RepoSearchResultFragment.newInstance());
        fragments.add(UserSearchResultFragment.newInstance());


        ImageButton closeButton = findViewById(R.id.searchCloseButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        ImageButton searchButton = findViewById(R.id.searchButton);
        final EditText searchContent = findViewById(R.id.searchContent);

        searchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){

                    InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(manager.isActive() && getCurrentFocus() != null){
                        if(getCurrentFocus().getWindowToken() != null){
                            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }

                    String content = textView.getText().toString();
                    for(SearchResultFragment fragment: fragments){
                        fragment.search(content);
                    }
                    return true;
                }
                return false;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(manager.isActive() && getCurrentFocus() != null){
                    if(getCurrentFocus().getWindowToken() != null){
                        manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }

                String content = searchContent.getText().toString();
                for(SearchResultFragment fragment: fragments){
                    fragment.search(content);
                }
            }
        });

        final ViewPager viewPager = findViewById(R.id.searchViewPager);
        TabLayout tabLayout = findViewById(R.id.searchTabLayout);


        viewPager.setAdapter(new FragmentPageAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab != null){
                    viewPager.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_slide_up,R.anim.activity_slide_down);
    }
}