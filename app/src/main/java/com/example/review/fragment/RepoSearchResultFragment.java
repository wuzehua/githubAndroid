package com.example.review.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.review.api.model.Repo;
import com.example.review.api.model.SearchResult;
import com.example.review.api.service.GithubService;
import com.example.review.rv.RepoViewAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoSearchResultFragment extends  SearchResultFragment {

    private RepoViewAdapter mAdapter;

    public RepoSearchResultFragment(GithubService service) {
        super(service);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mAdapter = new RepoViewAdapter();
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    protected void fetchData() {
        super.fetchData();
        Call<SearchResult<Repo>> call = mService.getSearchRepos(mSearchContent, accessToken);

        call.enqueue(new Callback<SearchResult<Repo>>() {
            @Override
            public void onResponse(Call<SearchResult<Repo>> call, Response<SearchResult<Repo>> response) {
                if(response.isSuccessful() && response.body() != null){
                    hasRequest = false;
                    mAdapter.setData(response.body().getItems());
                    mAdapter.notifyDataSetChanged();
                }
                isRequesting = false;
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<SearchResult<Repo>> call, Throwable t) {
                isRequesting = false;
                mRefreshLayout.setRefreshing(false);
            }
        });
    }
}
