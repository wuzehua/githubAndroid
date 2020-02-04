package com.example.review.ui.rv;

import android.view.ViewGroup;

import com.example.review.api.model.Release;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReleaseViewAdapter extends RecyclerView.Adapter<ReleaseViewHolder> {

    private List<Release> releases;

    public ReleaseViewAdapter(){
        super();
        releases = null;
    }

    @NonNull
    @Override
    public ReleaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ReleaseViewHolder.create(parent.getContext(), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ReleaseViewHolder holder, int position) {
        if(releases != null){
            holder.bind(releases.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return releases == null ? 0 : releases.size();
    }


    public void setReleases(List<Release> data){
        releases = data;
    }
}
