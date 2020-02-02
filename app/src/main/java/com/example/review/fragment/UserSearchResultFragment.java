package com.example.review.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.review.api.model.SearchResult;
import com.example.review.api.model.UserInfo;
import com.example.review.api.service.GithubService;
import com.example.review.rv.UserViewAdapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSearchResultFragment extends SearchResultFragment {

    private UserViewAdapter mAdapter;

    public UserSearchResultFragment(GithubService service) {
        super(service);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mAdapter = new UserViewAdapter();
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    protected void fetchData() {
        super.fetchData();
        Call<SearchResult<UserInfo>> call = mService.getSearchUsers(mSearchContent, accessToken);
        call.enqueue(new Callback<SearchResult<UserInfo>>() {
            @Override
            public void onResponse(Call<SearchResult<UserInfo>> call, Response<SearchResult<UserInfo>> response) {
                if(response.isSuccessful() && response.body() != null){
                    hasRequest = false;
                    mAdapter.setUsers(response.body().getItems());
                    mAdapter.notifyDataSetChanged();
                }

                mRefreshLayout.setRefreshing(false);
                isRequesting = false;
            }

            @Override
            public void onFailure(Call<SearchResult<UserInfo>> call, Throwable t) {
                mRefreshLayout.setRefreshing(false);
                isRequesting = false;
            }
        });

    }
}
