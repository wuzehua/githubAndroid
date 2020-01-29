package com.example.review.rv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView mPrivateText;
    private LinearLayout mLinearLayout;

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
        mPrivateText = itemView.findViewById(R.id.privateTagText);
        mLinearLayout = itemView.findViewById(R.id.repoLinearLayout);

        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Click",Toast.LENGTH_SHORT).show();
            }
        });
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

            mStarCount.setText(String.format("Star %d", data.getStar()));
            mForkCount.setText(String.format("Fork %d",data.getFork()));
            mLanguageText.setText(data.getLanguage());


        }

    }

}
