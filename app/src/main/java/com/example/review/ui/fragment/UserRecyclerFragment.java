package com.example.review.ui.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.review.R;
import com.example.review.api.model.UserInfo;
import com.example.review.api.service.GithubService;
import com.example.review.ui.rv.UserViewAdapter;
import com.example.review.utils.GithubServiceUtils;

import java.util.List;


public class UserRecyclerFragment extends Fragment {


    private String userName;
    private String getType;
    private String accessToken;
    private SwipeRefreshLayout mRefreshLayout;
    private UserViewAdapter mAdapter;
    private boolean hasLoaded;

    public enum Type{
        FOLLOWER,FOLLOWING
    }


    public UserRecyclerFragment(){

    }

    public static UserRecyclerFragment newInstance(Type type) {

        Bundle args = new Bundle();
        if(type == Type.FOLLOWER){
            args.putString("userType","followers");
        }else {
            args.putString("userType","following");
        }

        args.putString("userName", "user");
        UserRecyclerFragment fragment = new UserRecyclerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static UserRecyclerFragment newInstance(Type type,@NonNull String userName) {

        Bundle args = new Bundle();
        if(type == Type.FOLLOWER){
            args.putString("userType","followers");
        }else {
            args.putString("userType","following");
        }

        args.putString("userName", "users/" + userName);
        UserRecyclerFragment fragment = new UserRecyclerFragment();
        fragment.setArguments(args);
        return fragment;
    }

//    public UserRecyclerFragment(Type type) {
//        super();
//        if(type == Type.FOLLOWER){
//            getType = "followers";
//        }else {
//            getType = "following";
//        }
//        hasLoaded = false;
//        userName = "user";
//    }
//
//    public UserRecyclerFragment(Type type, String userName){
//        super();
//        if(type == Type.FOLLOWER){
//            getType = "followers";
//        }else {
//            getType = "following";
//        }
//        hasLoaded = false;
//        this.userName = "users/" + userName;
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getArguments().getString("userName");
        hasLoaded = false;
        getType = getArguments().getString("userType");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);
        RecyclerView mRecyclerView = view.findViewById(R.id.mainRecyclerView);
        mRefreshLayout = view.findViewById(R.id.swipeLayout);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        accessToken = getActivity().getSharedPreferences("loginStat", Context.MODE_PRIVATE).getString("accessToken","");

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });

        mAdapter = new UserViewAdapter();
        mRecyclerView.setAdapter(mAdapter);

//        if(!hasLoaded){
//            mRefreshLayout.setRefreshing(true);
//            fetchData();
//        }

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
        Call<List<UserInfo>> call = GithubServiceUtils.getGithubApiService().getUsers(userName, getType, accessToken);
        call.enqueue(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
                if(response.isSuccessful() && response.body() != null){
                    mAdapter.setUsers(response.body());
                    mAdapter.notifyDataSetChanged();
                    hasLoaded = true;
                }
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<UserInfo>> call, Throwable t) {
                mRefreshLayout.setRefreshing(false);
            }
        });
    }
}