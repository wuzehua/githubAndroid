package com.example.review.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.review.R;
import com.example.review.api.service.GithubService;
import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class ContentBaseFragment extends Fragment {
    private GithubService mService;
    private ViewPager mViewPager;
    private String repoFullName;
    ArrayList<String> titles;

    public ContentBaseFragment(GithubService service, @NonNull String fullName){
        super();
        mService = service;
        repoFullName = fullName;
        initTitles();
    }

    private void initTitles(){
        titles = new ArrayList<>();
        Collections.addAll(titles,"Files","Commits");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        TabLayout mTabLayout = view.findViewById(R.id.repoTabLayout);
        mViewPager = view.findViewById(R.id.baseFragmentViewPager);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new FilesFragment(mService, repoFullName));
        fragments.add(new CommitFragment(mService, repoFullName));

        mViewPager.setAdapter(new FragmentPageAdapter(getFragmentManager(), fragments, titles));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mTabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab != null){
                    mViewPager.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        return view;
    }
}
