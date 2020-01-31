package com.example.review.rv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.review.R;
import com.example.review.activity.UserDetailActivity;
import com.example.review.api.model.UserInfo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImageView;
    private TextView mLoginText;
    private LinearLayout mLinearLayout;

    public UserViewHolder(@NonNull View view){
        super(view);
        mImageView = view.findViewById(R.id.userImage);
        mLoginText = view.findViewById(R.id.userLoginText);
        mLinearLayout = view.findViewById(R.id.userLinearLayout);
    }

    public static UserViewHolder create(Context context, ViewGroup root){
        return new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.user_view_holder, root, false));
    }


    public void bind(UserInfo data, Context context){
        if(data != null){
            Glide.with(context)
                    .load(data.getHeadImageUrl())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                    .into(mImageView);
            final String userName = data.getLogin();
            mLoginText.setText(data.getLogin());
            mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), UserDetailActivity.class);
                    intent.putExtra("userName", userName);
                    view.getContext().startActivity(intent);
                    ((Activity)view.getContext()).overridePendingTransition(R.anim.activity_slide_in,R.anim.activity_slide_out);
                }
            });
        }
    }

}
