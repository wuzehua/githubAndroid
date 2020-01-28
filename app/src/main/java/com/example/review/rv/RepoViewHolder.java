package com.example.review.rv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.review.R;
import com.example.review.api.model.Repo;


public class RepoViewHolder extends RecyclerView.ViewHolder {

    private TextView mRepoNameTextView;
    private TextView mDescription;
    private TextView mStarCount;
    private TextView mForkCount;
    private TextView mLanguageText;

    public static RepoViewHolder create(Context context, ViewGroup root){
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_holder, root, false);
        return new RepoViewHolder(view);
    }

    public RepoViewHolder(@NonNull View itemView) {
        super(itemView);
        mRepoNameTextView = itemView.findViewById(R.id.repoName);
        mDescription = itemView.findViewById(R.id.repoDescription);
        mStarCount = itemView.findViewById(R.id.starCount);
        mForkCount = itemView.findViewById(R.id.forkCount);
        mLanguageText = itemView.findViewById(R.id.languageText);

    }


    public void bind(Repo data){
        if(data != null){
            mRepoNameTextView.setText(data.getName());
            if(data.getDescription() == null){
                mDescription.setText("no description");
            }else{

                mDescription.setText(data.getDescription());
            }

            mStarCount.setText(String.format("Star %d", data.getStar()));
            mForkCount.setText(String.format("Fork %d",data.getFork()));
            mLanguageText.setText(data.getLanguage());


        }

    }

}
