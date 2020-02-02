package com.example.review.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.review.R;
import com.example.review.api.service.GithubService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class SearchResultFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mRefreshLayout;
    protected String mSearchContent;
    protected boolean hasRequest;
    protected boolean isInteractive;
    protected boolean isRequesting;
    protected GithubService mService;
    protected String accessToken;

    public SearchResultFragment(GithubService service){
        super();
        mService = service;
        isInteractive = false;
        hasRequest = false;
        isRequesting = false;
        mSearchContent = "";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.mainRecyclerView);
        mRefreshLayout = view.findViewById(R.id.swipeLayout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        accessToken = getActivity().getSharedPreferences("loginStat", Context.MODE_PRIVATE).getString("accessToken","");

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        isInteractive = true;
        if(hasRequest && !isRequesting){
            mRefreshLayout.setRefreshing(true);
            fetchData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isInteractive = false;
    }

    public void search(String searchContent){
        if(searchContent != null && !searchContent.equals("")) {
            mSearchContent = searchContent;
            hasRequest = true;
            if (isInteractive) {
                mRefreshLayout.setRefreshing(true);
                fetchData();
            }
        }
    }

    protected void fetchData(){
        isRequesting = true;
    }


}
