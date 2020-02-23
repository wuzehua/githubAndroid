package com.example.review.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.review.api.model.Repo;
import com.example.review.api.model.SearchResult;
import com.example.review.ui.rv.RepoViewAdapter;
import com.example.review.utils.GithubServiceUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoSearchResultFragment extends  SearchResultFragment {

    private RepoViewAdapter mAdapter;

    public RepoSearchResultFragment() {
    }

    public static RepoSearchResultFragment newInstance() {

        Bundle args = new Bundle();

        RepoSearchResultFragment fragment = new RepoSearchResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    protected void fetchData(final boolean more) {
        super.fetchData(more);
        Call<SearchResult<Repo>> call = GithubServiceUtils.getGithubApiService().getSearchRepos(mSearchContent, accessToken, requestPage);

        call.enqueue(new Callback<SearchResult<Repo>>() {
            @Override
            public void onResponse(Call<SearchResult<Repo>> call, Response<SearchResult<Repo>> response) {
                if(response.isSuccessful() && response.body() != null){

                    if(response.body().getItems().size() < GithubServiceUtils.RESPONSE_COUNT_PER_PAGE){
                        isTheEnd = true;
                    }

                    hasRequest = false;

                    if(more){
                        mAdapter.addData(response.body().getItems());
                    }else {
                        mAdapter.setData(response.body().getItems());
                    }
                    mAdapter.notifyDataSetChanged();
                    requestPage += 1;
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
