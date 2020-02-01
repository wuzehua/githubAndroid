package com.example.review.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.review.R;
import com.example.review.api.service.GithubService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
            fetchData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isInteractive = false;
    }

    public void search(String searchContent){
        mSearchContent = searchContent;
        hasRequest = true;
        if(isInteractive){
            mRefreshLayout.setRefreshing(true);
            fetchData();
        }
    }

    protected void fetchData(){
        isRequesting = true;
    }


}
