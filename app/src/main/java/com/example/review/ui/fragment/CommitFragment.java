package com.example.review.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.review.R;
import com.example.review.api.model.CommitInfo;
import com.example.review.ui.rv.CommitViewAdapter;
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

public class CommitFragment extends Fragment {

    private String repoFullName;
    private SwipeRefreshLayout refreshLayout;
    private CommitViewAdapter mAdapter;
    private String accessToken;
    private boolean hasLoaded;
    private int requestPage;
    private boolean isTheEnd;

    public static CommitFragment newInstance(@NonNull String fullName) {

        Bundle args = new Bundle();
        args.putString("fullName", fullName);
        CommitFragment fragment = new CommitFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public CommitFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasLoaded = false;
        repoFullName = getArguments().getString("fullName");
        requestPage = 1;
        isTheEnd = false;
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


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()){
                    if(!isTheEnd){
                        refreshLayout.setRefreshing(true);
                        fetchData(true);
                    }else {
                        Toast.makeText(recyclerView.getContext(), "The end", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestPage = 1;
                isTheEnd = false;
                fetchData(false);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!hasLoaded){
            refreshLayout.setRefreshing(true);
            fetchData(false);
        }
    }

    private void fetchData(final boolean more){
        Call<List<CommitInfo>> call = GithubServiceUtils.getGithubApiService().getCommitsInfo(repoFullName, accessToken, requestPage);
        call.enqueue(new Callback<List<CommitInfo>>() {
            @Override
            public void onResponse(Call<List<CommitInfo>> call, Response<List<CommitInfo>> response) {
                if(response.isSuccessful() && response.body() != null){

                    if(response.body().size() < GithubServiceUtils.RESPONSE_COUNT_PER_PAGE){
                        isTheEnd = true;
                    }

                    if(more){
                        mAdapter.addCommitInfoList(response.body());
                    }else {
                        mAdapter.setCommitInfoList(response.body());
                    }

                    requestPage += 1;

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
