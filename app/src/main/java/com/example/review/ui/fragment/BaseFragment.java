package com.example.review.ui.fragment;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.review.R;
import com.example.review.api.service.GithubService;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class BaseFragment extends Fragment {


    private ViewPager mViewPager;
    private ArrayList<String> titles;
    private String userName;
    private boolean hasName;

    public static BaseFragment newInstance() {
        Bundle args = new Bundle();
        BaseFragment fragment = new BaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static BaseFragment newInstance(@NonNull String userName){
        Bundle args = new Bundle();
        BaseFragment fragment = new BaseFragment();
        args.putString("userName", userName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!= null && !getArguments().isEmpty()){
            hasName = true;
            userName = getArguments().getString("userName");
        }else{
            hasName = false;
            userName = null;
        }

        initTitles();
    }

    public BaseFragment(){
    }


    private void initTitles(){
        titles = new ArrayList<>();
        Collections.addAll(titles, "Info","Repo", "Star","Followers","Following");
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_base, container, false);
        TabLayout mTabLayout = view.findViewById(R.id.repoTabLayout);
        mViewPager = view.findViewById(R.id.baseFragmentViewPager);

        ArrayList<Fragment> fragments = new ArrayList<>();

        if(!hasName) {
            fragments.add(UserInfoFragment.newInstance());
            fragments.add(RepoRecyclerFragment.newInstance(RepoRecyclerFragment.Type.Repos));
            fragments.add(RepoRecyclerFragment.newInstance(RepoRecyclerFragment.Type.Starred));
            fragments.add(UserRecyclerFragment.newInstance(UserRecyclerFragment.Type.FOLLOWER));
            fragments.add(UserRecyclerFragment.newInstance(UserRecyclerFragment.Type.FOLLOWING));
        }else {
            fragments.add(UserInfoFragment.newInstance(userName));
            fragments.add(RepoRecyclerFragment.newInstance(RepoRecyclerFragment.Type.Repos, userName));
            fragments.add(RepoRecyclerFragment.newInstance(RepoRecyclerFragment.Type.Starred, userName));
            fragments.add(UserRecyclerFragment.newInstance(UserRecyclerFragment.Type.FOLLOWER, userName));
            fragments.add(UserRecyclerFragment.newInstance(UserRecyclerFragment.Type.FOLLOWING, userName));
        }


        mViewPager.setAdapter(new FragmentPageAdapter(getFragmentManager(), fragments, titles));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mTabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab != null){
                    mViewPager.setCurrentItem(tab.getPosition());
                    View view = tab.getCustomView();
                    if(view != null && view instanceof TextView) {
                        ((TextView)view).setTextSize(22);
                        Toast.makeText(getContext(), "Change size", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab != null){
                    View view = (TextView)tab.getCustomView();
                    if(view != null && view instanceof TextView) {
                        ((TextView)view).setTextSize(18);
                    }
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;


    }
}