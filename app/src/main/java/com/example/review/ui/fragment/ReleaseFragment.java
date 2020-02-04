package com.example.review.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.review.R;
import com.example.review.api.model.Release;
import com.example.review.ui.rv.ReleaseViewAdapter;
import com.example.review.utils.GithubServiceUtils;

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

public class ReleaseFragment extends Fragment {

    private String repoName;
    private String accessToken;
    private boolean hasLoaded;
    private ReleaseViewAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;

    public ReleaseFragment(){

    }

    public static ReleaseFragment newInstance(@NonNull String repoName) {

        Bundle args = new Bundle();
        args.putString("repoName", repoName);
        ReleaseFragment fragment = new ReleaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasLoaded = false;
        repoName = getArguments().getString("repoName");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.mainRecyclerView);

        mRefreshLayout = view.findViewById(R.id.swipeLayout);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });

        accessToken = getActivity().getSharedPreferences("loginStat", Context.MODE_PRIVATE).getString("accessToken","");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ReleaseViewAdapter();
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!hasLoaded){
            mRefreshLayout.setRefreshing(true);
            fetchData();
        }
    }

    private void fetchData(){
        Call<List<Release>> call = GithubServiceUtils.getGithubApiService().getReleases(repoName, accessToken);
        call.enqueue(new Callback<List<Release>>() {
            @Override
            public void onResponse(Call<List<Release>> call, Response<List<Release>> response) {
                if(response.isSuccessful() && response.body() != null){
                    mAdapter.setReleases(response.body());
                    mAdapter.notifyDataSetChanged();
                    hasLoaded = true;
                }
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Release>> call, Throwable t) {
                mRefreshLayout.setRefreshing(false);
            }
        });
    }
}
