package com.example.review.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    protected String accessToken;
    protected boolean isTheEnd;
    protected int requestPage;

    public static SearchResultFragment newInstance() {

        Bundle args = new Bundle();

        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SearchResultFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isInteractive = false;
        hasRequest = false;
        isRequesting = false;
        mSearchContent = "";
        isTheEnd = false;
        requestPage = 1;
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
                requestPage = 1;
                isTheEnd = false;
                fetchData(false);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()) {
                    if (!isTheEnd) {
                        mRefreshLayout.setRefreshing(true);
                        fetchData(true);
                    } else {
                        Toast.makeText(recyclerView.getContext(), "The end", Toast.LENGTH_SHORT).show();
                    }
                }
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
            requestPage = 1;
            isTheEnd = false;
            fetchData(false);
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
                isTheEnd = false;
                requestPage = 1;
                mRefreshLayout.setRefreshing(true);
                fetchData(false);
            }
        }
    }

    protected void fetchData(final boolean more){
        isRequesting = true;
    }


}
