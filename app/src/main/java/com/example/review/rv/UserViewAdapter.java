package com.example.review.rv;

import android.content.Context;
import android.view.ViewGroup;

import com.example.review.api.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private Context mContext;
    private List<UserInfo> users;

    public UserViewAdapter(){
        super();
        users = new ArrayList<>();
        mContext = null;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }

        return UserViewHolder.create(mContext,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(users.get(position), mContext);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<UserInfo> data){
        users = data;
    }

}
