package com.example.review.fragment;

import android.content.Context;
import android.os.Bundle;
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
import com.example.review.api.model.Repo;

import java.util.List;



public class RepoRecyclerFragment extends Fragment {

    public enum Type{
        Repos,Starred
    }

    private GithubService mApiGithubService;
    private RepoViewAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private String accessToken;
    private boolean hasLoaded;
    private String repoType;
    private String userName;

    public RepoRecyclerFragment(GithubService service, Type type) {
        super();
        mApiGithubService = service;
        hasLoaded = false;
        userName = "user";
        if(type == Type.Repos){
            repoType = "repos";
        }else{
            repoType = "starred";
        }
    }

    public RepoRecyclerFragment(GithubService service, Type type, String userName) {
        super();
        mApiGithubService = service;
        hasLoaded = false;
        this.userName = "users/" + userName;
        if(type == Type.Repos){
            repoType = "repos";
        }else{
            repoType = "starred";
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);

        RecyclerView mRecyclerView = view.findViewById(R.id.mainRecyclerView);
        mRefreshLayout = view.findViewById(R.id.swipeLayout);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


        accessToken = getActivity().getSharedPreferences("loginStat", Context.MODE_PRIVATE).getString("accessToken","");

        mAdapter = new RepoViewAdapter();

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });

        mRecyclerView.setAdapter(mAdapter);


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
        Call<List<Repo>> call = mApiGithubService.getRepos(userName, repoType, accessToken);

        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if(response.body() != null && response.isSuccessful()){
                    //Toast.makeText(getContext(), "Refresh finish", Toast.LENGTH_SHORT).show();
                    mAdapter.setData(response.body());
                    mAdapter.notifyDataSetChanged();
                    hasLoaded = true;
                }
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Toast.makeText(getContext(), "Fetch repos failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
