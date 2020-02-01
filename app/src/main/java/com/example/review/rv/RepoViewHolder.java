package com.example.review.rv;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.review.R;
import com.example.review.activity.ContentActivity;
import com.example.review.api.model.Repo;



public class RepoViewHolder extends RecyclerView.ViewHolder {

    private TextView mRepoNameTextView;
    private TextView mDescription;
    private TextView mStarCount;
    private TextView mForkCount;
    private TextView mLanguageText;
    private TextView mPrivateText;
    private LinearLayout mLinearLayout;


    public static RepoViewHolder create(Context context, ViewGroup root){
        return new RepoViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_holder, root, false));
    }

    public RepoViewHolder(@NonNull View itemView) {
        super(itemView);
        mRepoNameTextView = itemView.findViewById(R.id.repoName);
        mDescription = itemView.findViewById(R.id.repoDescription);
        mStarCount = itemView.findViewById(R.id.starCount);
        mForkCount = itemView.findViewById(R.id.forkCount);
        mLanguageText = itemView.findViewById(R.id.languageText);
        mPrivateText = itemView.findViewById(R.id.privateTagText);
        mLinearLayout = itemView.findViewById(R.id.repoLinearLayout);

    }


    public void bind(Repo data){
        if(data != null){
            mRepoNameTextView.setText(data.getName());
            if(data.getDescription() == null){
                mDescription.setText("no description");
            }else{

                mDescription.setText(data.getDescription());
            }

            if(data.isPrivate()){
                mPrivateText.setText("private");
                mPrivateText.setBackgroundResource(R.drawable.private_tag_background);
            }else {
                mPrivateText.setText("public");
                mPrivateText.setBackgroundResource(R.drawable.public_tag_background);
            }

            final String name = data.getName();
            mStarCount.setText(String.format("%d", data.getStar()));
            mForkCount.setText(String.format("%d",data.getFork()));
            mLanguageText.setText(data.getLanguage());

            mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ContentActivity.class);
                    intent.putExtra("fullName", name);
                    view.getContext().startActivity(intent);
                    ((Activity)view.getContext()).overridePendingTransition(R.anim.activity_slide_in,R.anim.activity_slide_out);
                }
            });
        }

    }

}
