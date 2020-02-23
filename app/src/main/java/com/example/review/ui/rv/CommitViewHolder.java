package com.example.review.ui.rv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.review.R;
import com.example.review.api.model.CommitInfo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CommitViewHolder extends RecyclerView.ViewHolder {

    private TextView mLoginText;
    private TextView mDateText;
    private TextView mMessageText;
    private TextView mShaText;
    private ImageView mImageView;

    public static CommitViewHolder create(Context context, ViewGroup root){
        return new CommitViewHolder(LayoutInflater.from(context).inflate(R.layout.commit_view_holder, root, false));
    }


    public CommitViewHolder(@NonNull View view){
        super(view);
        mLoginText = view.findViewById(R.id.commitLoginText);
        mDateText = view.findViewById(R.id.commitDateText);
        mMessageText = view.findViewById(R.id.commitMessageText);
        mShaText = view.findViewById(R.id.commitShaText);
        mImageView = view.findViewById(R.id.committerImage);
    }

    public void bind(CommitInfo data, Context context){
        if(data != null){
            if(data.getCommitter() != null) {
                Glide.with(context)
                        .load(data.getCommitter().getHeadImageUrl())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                        .into(mImageView);

                mLoginText.setText(data.getCommitter().getLogin());
            }else {
                mImageView.setForeground(context.getResources().getDrawable(R.drawable.ic_action_user, context.getTheme()));
                mLoginText.setText("");
            }
            mDateText.setText(data.getCommit().getCommitter().getDate());
            mMessageText.setText(data.getCommit().getMessage());
            mShaText.setText(data.getSha().substring(0,6));
        }
    }
}
