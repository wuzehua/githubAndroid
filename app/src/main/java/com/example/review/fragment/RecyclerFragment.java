package com.example.review.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.review.R;
import com.example.review.api.service.GithubService;
import com.example.review.rv.RepoViewAdapter;
import com.example.review.rv.RepoViewHolder;
import com.example.review.api.model.Repo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;



public class RecyclerFragment extends Fragment {

    private GithubService mApiGithubService;
    private RepoViewAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;

    public RecyclerFragment(GithubService service) {
        super();
        mApiGithubService = service;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.mainRecyclerView);
        mRefreshLayout = view.findViewById(R.id.swipeLayout);
        mRefreshLayout.setRefreshing(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        mAdapter = new RepoViewAdapter();

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });



        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        fetchData();
        return view;
    }

    private void fetchData(){
        Call<List<Repo>> call = mApiGithubService.getUserRepos("wuzehua");
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if(response.body() != null && response.isSuccessful()){
                    Toast.makeText(getContext(), "Refresh finish", Toast.LENGTH_SHORT).show();
                    mAdapter.setData(response.body());
                    mAdapter.notifyDataSetChanged();
                    mRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Toast.makeText(getContext(), "Fetch repos failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
