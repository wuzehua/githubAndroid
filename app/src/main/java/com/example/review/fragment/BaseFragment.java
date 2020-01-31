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
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class BaseFragment extends Fragment {

    private GithubService mService;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ArrayList<String> titles;
    private String userName;
    private boolean hasName;

    public BaseFragment(GithubService service){
        mService = service;
        initTitles();
        hasName = false;
        userName = null;
    }

    public BaseFragment(GithubService service, @NonNull String userName){
        mService = service;
        initTitles();
        hasName = true;
        this.userName = userName;
    }

    private void initTitles(){
        titles = new ArrayList<>();
        Collections.addAll(titles, "Info","Repo", "Star","Followers","Following");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_base, container, false);
        mTabLayout = view.findViewById(R.id.repoTabLayout);
        mViewPager = view.findViewById(R.id.baseFragmentViewPager);

        ArrayList<Fragment> fragments = new ArrayList<>();

        if(!hasName) {
            fragments.add(new UserInfoFragment(mService));
            fragments.add(new RepoRecyclerFragment(mService, RepoRecyclerFragment.Type.Repos));
            fragments.add(new RepoRecyclerFragment(mService, RepoRecyclerFragment.Type.Starred));
            fragments.add(new UserRecyclerFragment(mService, UserRecyclerFragment.Type.FOLLOWER));
            fragments.add(new UserRecyclerFragment(mService, UserRecyclerFragment.Type.FOLLOWING));
        }else {
            fragments.add(new UserInfoFragment(mService, userName));
            fragments.add(new RepoRecyclerFragment(mService, RepoRecyclerFragment.Type.Repos, userName));
            fragments.add(new RepoRecyclerFragment(mService, RepoRecyclerFragment.Type.Starred, userName));
            fragments.add(new UserRecyclerFragment(mService, UserRecyclerFragment.Type.FOLLOWER, userName));
            fragments.add(new UserRecyclerFragment(mService, UserRecyclerFragment.Type.FOLLOWING, userName));
        }


        mViewPager.setAdapter(new FragmentPageAdapter(getFragmentManager(), fragments, titles));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

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