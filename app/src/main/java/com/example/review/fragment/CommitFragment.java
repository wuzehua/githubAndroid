package com.example.review.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.review.R;
import com.example.review.api.model.CommitInfo;
import com.example.review.api.service.GithubService;
import com.example.review.rv.CommitViewAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommitFragment extends Fragment {

    private GithubService mService;
    private String repoFullName;
    private SwipeRefreshLayout refreshLayout;
    private CommitViewAdapter mAdapter;
    private String accessToken;
    private boolean hasLoaded;


    public CommitFragment(GithubService service, @NonNull String fullName){
        super();
        mService = service;
        repoFullName = fullName;
        hasLoaded = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.mainRecyclerView);
        refreshLayout = view.findViewById(R.id.swipeLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CommitViewAdapter();
        recyclerView.setAdapter(mAdapter);

        accessToken = getActivity().getSharedPreferences("loginStat", Context.MODE_PRIVATE).getString("accessToken","");

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        if(!hasLoaded){
            refreshLayout.setRefreshing(true);
            fetchData();
        }
    }

    private void fetchData(){
        Call<List<CommitInfo>> call = mService.getCommitsInfo(repoFullName, accessToken);
        call.enqueue(new Callback<List<CommitInfo>>() {
            @Override
            public void onResponse(Call<List<CommitInfo>> call, Response<List<CommitInfo>> response) {
                if(response.isSuccessful() && response.body() != null){
                    mAdapter.setCommitInfoList(response.body());
                    mAdapter.notifyDataSetChanged();
                    hasLoaded = true;
                }
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<CommitInfo>> call, Throwable t) {
                refreshLayout.setRefreshing(false);
            }
        });
    }
}
