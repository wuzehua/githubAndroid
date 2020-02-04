package com.example.review.ui.rv;

import android.content.Context;
import android.view.ViewGroup;

import com.example.review.api.model.Asset;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AssetViewAdapter extends RecyclerView.Adapter<AssetViewHolder> {

    private List<Asset> assets;
    private Context mContext;

    public AssetViewAdapter(){
        super();
        assets = null;
        mContext = null;
    }

    @NonNull
    @Override
    public AssetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }

        return AssetViewHolder.create(mContext, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AssetViewHolder holder, int position) {
        if(assets != null){
            holder.bind(assets.get(position), mContext);
        }
    }

    @Override
    public int getItemCount() {
        return assets == null ? 0 : assets.size();
    }

    public void setAssets(List<Asset> data){
        assets = data;
    }
}
