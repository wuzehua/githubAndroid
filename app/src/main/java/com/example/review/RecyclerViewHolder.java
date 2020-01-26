package com.example.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView mTextView;

    public static RecyclerViewHolder create(Context context, ViewGroup root){
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_holder, root, false);
        return new RecyclerViewHolder(view);
    }

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.viewHolderTextView);
    }


    public void bind(String data){
        if(data != null){
            mTextView.setText(data);
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),mTextView.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}
