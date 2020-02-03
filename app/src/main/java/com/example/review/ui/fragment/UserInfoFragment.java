package com.example.review.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.review.R;
import com.example.review.api.model.UserInfo;
import com.example.review.api.service.GithubService;
import com.example.review.utils.GithubServiceUtils;


import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoFragment extends Fragment {

    private String accessToken;

    private TextView mInfoEmailText;
    private TextView mInfoNameText;
    private TextView mInfoLoginText;
    private ImageView mImageView;
    private TextView mLocationText;
    private TextView mCompanyText;
    private TextView mInfoBioText;
    private SwipeRefreshLayout refreshLayout;
    private String userName;
    private boolean hasLoaded;


    public static UserInfoFragment newInstance() {

        Bundle args = new Bundle();
        args.putString("userName","user");
        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static UserInfoFragment newInstance(@NonNull String userName) {

        Bundle args = new Bundle();
        args.putString("userName","users/" + userName);
        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public UserInfoFragment(){

    }
//    public UserInfoFragment(){
//        super();
//        userName = "user";
//        hasLoaded = false;
//    }
//
//    public UserInfoFragment(String userName){
//        super();
//        this.userName = "users/" + userName;
//        hasLoaded = false;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasLoaded = false;
        userName = getArguments().getString("userName");
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



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!hasLoaded) {
            refreshLayout.setRefreshing(true);
            fetchData();
        }
    }

    private void fetchData(){
        Call<UserInfo> call = GithubServiceUtils.getGithubApiService().getInfo(userName,accessToken);

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
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                            .into(mImageView);
                    hasLoaded = true;
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
