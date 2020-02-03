package com.example.review.ui.fragment;

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
import com.example.review.ui.rv.RepoViewAdapter;
import com.example.review.api.model.Repo;
import com.example.review.utils.GithubServiceUtils;

import java.util.List;



public class RepoRecyclerFragment extends Fragment {

    public enum Type{
        Repos,Starred
    }

    private RepoViewAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private String accessToken;
    private boolean hasLoaded;
    private String repoType;
    private String userName;


    public RepoRecyclerFragment(){

    }

    public static RepoRecyclerFragment newInstance(Type type) {

        Bundle args = new Bundle();

        RepoRecyclerFragment fragment = new RepoRecyclerFragment();
        if(type == Type.Repos){
            args.putString("repoType","repos");
        }else {
            args.putString("repoType","starred");
        }
        args.putString("userName", "user");

        fragment.setArguments(args);
        return fragment;
    }


    public static RepoRecyclerFragment newInstance(Type type, @NonNull String userName) {

        Bundle args = new Bundle();

        RepoRecyclerFragment fragment = new RepoRecyclerFragment();
        if(type == Type.Repos){
            args.putString("repoType","repos");
        }else {
            args.putString("repoType","starred");
        }

        args.putString("userName", "users/" + userName);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasLoaded = false;
        userName = getArguments().getString("userName");
        repoType = getArguments().getString("repoType");
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
        Call<List<Repo>> call = GithubServiceUtils.getGithubApiService().getRepos(userName, repoType, accessToken);

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
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

}
