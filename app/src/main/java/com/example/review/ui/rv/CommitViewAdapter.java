package com.example.review.ui.rv;

import android.content.Context;
import android.view.ViewGroup;

import com.example.review.api.model.CommitInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommitViewAdapter extends RecyclerView.Adapter<CommitViewHolder> {

    private Context mContext;
    private List<CommitInfo> commitInfoList;

    public CommitViewAdapter(){
        super();
        mContext = null;
        commitInfoList = null;
    }

    @NonNull
    @Override
    public CommitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        return CommitViewHolder.create(mContext,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CommitViewHolder holder, int position) {
        if(commitInfoList != null) {
            holder.bind(commitInfoList.get(position), mContext);
        }
    }

    @Override
    public int getItemCount() {
        return commitInfoList == null ? 0 : commitInfoList.size();
    }

    public void setCommitInfoList(List<CommitInfo> data){
        commitInfoList = data;
    }

}
