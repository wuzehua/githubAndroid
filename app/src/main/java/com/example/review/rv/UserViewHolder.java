package com.example.review.rv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.review.R;
import com.example.review.api.model.UserInfo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImageView;
    private TextView mLoginText;
    private TextView mEmailText;
    private LinearLayout mLinearLayout;

    public UserViewHolder(@NonNull View view){
        super(view);
        mImageView = view.findViewById(R.id.userImage);
        mLoginText = view.findViewById(R.id.userLoginText);
        mEmailText = view.findViewById(R.id.userEmailText);
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

            mLoginText.setText(data.getLogin());
            mEmailText.setText(data.getEmail());
            mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Tap",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
