package com.example.review.ui.rv;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.review.api.model.Repo;

import java.util.ArrayList;
import java.util.List;

public class RepoViewAdapter extends RecyclerView.Adapter<RepoViewHolder> {

    private List<Repo> data;

    public RepoViewAdapter(){
        super();
        data = null;
    }


    public RepoViewAdapter(List<Repo> data){
        super();
        this.data = data;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return RepoViewHolder.create(parent.getContext(), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        if(data != null) {
            holder.bind(data.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<Repo> newData){
        data = newData;
    }

}
