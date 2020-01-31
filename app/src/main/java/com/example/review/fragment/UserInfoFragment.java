package com.example.review.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.review.R;
import com.example.review.api.model.UserInfo;
import com.example.review.api.service.GithubService;


import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoFragment extends Fragment {

    private GithubService mService;
    private String accessToken;

    private TextView mInfoEmailText;
    private TextView mInfoNameText;
    private TextView mInfoLoginText;
    private ImageView mImageView;
    private TextView mLocationText;
    private TextView mCompanyText;
    private TextView mInfoBioText;
    private SwipeRefreshLayout refreshLayout;
    private Type type;
    private String userName;

    public enum Type{
        PRIVATE, PUBLIC
    }

    public UserInfoFragment(GithubService service){
        super();
        mService = service;
        type = Type.PRIVATE;
        userName = "";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        mInfoNameText = view.findViewById(R.id.infoNameText);
        mInfoLoginText = view.findViewById(R.id.infoLoginText);
        mLocationText = view.findViewById(R.id.infoLocationText);
        mInfoEmailText = view.findViewById(R.id.infoEmailText);
        mImageView = view.findViewById(R.id.userHeadImageView);
        mCompanyText = view.findViewById(R.id.infoCompanyText);
        mInfoBioText = view.findViewById(R.id.infoBioText);
        refreshLayout = view.findViewById(R.id.infoRefreshLayout);
        accessToken = getActivity().getSharedPreferences("loginStat", Context.MODE_PRIVATE).getString("accessToken","");

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });

        refreshLayout.setRefreshing(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
    }

    private void fetchData(){
        Call<UserInfo> call;
        if(type == Type.PRIVATE) {
            call = mService.getInfo(accessToken);
        }else{
            call = mService.getInfo(accessToken);
        }

        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if(response.isSuccessful() && response.body() != null){
                    UserInfo info = response.body();
                    mInfoLoginText.setText(info.getLogin());
                    mInfoNameText.setText(info.getName());
                    mLocationText.setText(info.getLocation());
                    mInfoEmailText.setText(info.getEmail());
                    mCompanyText.setText(info.getCompany());
                    mInfoBioText.setText(info.getBio());
                    Glide.with(Objects.requireNonNull(getContext()))
                            .load(info.getHeadImageUrl())
                            .into(mImageView);
                }
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Toast.makeText(getContext(),"Get Info Failed",Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }


}
