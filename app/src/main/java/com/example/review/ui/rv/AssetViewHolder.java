package com.example.review.ui.rv;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.review.R;
import com.example.review.api.model.Asset;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AssetViewHolder extends RecyclerView.ViewHolder {

    private LinearLayout mFileLinearLayout;
    private TextView mFilenameText;

    public static AssetViewHolder create(Context context, ViewGroup root){
        return new AssetViewHolder(LayoutInflater.from(context).inflate(R.layout.file_view_holder, root, false));
    }

    public AssetViewHolder(@NonNull View view){
        super(view);
        mFileLinearLayout = view.findViewById(R.id.fileLinearLayout);
        mFilenameText = view.findViewById(R.id.fileNameText);
    }

    public void bind(Asset data, Context context){
        if(data != null){

            mFilenameText.setText(data.getName());
            mFileLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Click", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
