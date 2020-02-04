package com.example.review.ui.rv;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.review.R;
import com.example.review.api.model.Release;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReleaseViewHolder extends RecyclerView.ViewHolder {

    private TextView mReleaseText;
    private TextView mReleaseDateText;
    private LinearLayout mLinearLayout;

    public static ReleaseViewHolder create(Context context, ViewGroup parent){
        return new ReleaseViewHolder(LayoutInflater.from(context).inflate(R.layout.release_view_holder, parent, false));
    }

    public ReleaseViewHolder(@NonNull View view){
        super(view);
        mReleaseText = view.findViewById(R.id.releaseText);
        mReleaseDateText = view.findViewById(R.id.releaseDateText);
        mLinearLayout = view.findViewById(R.id.releaseLinearLayout);
    }

    public void bind(final Release data){
        if(data != null){
            mReleaseText.setText(data.getName());
            mReleaseDateText.setText(data.getPublishDate());
            mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog dialog = new AlertDialog.Builder(view.getContext()).create();
                    dialog.setTitle("Assets");
                    dialog.setCancelable(true);


                    if(data.getAssets() != null && data.getAssets().size() > 0) {
                        AssetViewAdapter adapter = new AssetViewAdapter();
                        adapter.setAssets(data.getAssets());
                        RecyclerView recyclerView = new RecyclerView(view.getContext());
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

                        dialog.setView(recyclerView);

                        adapter.notifyDataSetChanged();
                    }else {
                        TextView textView = new TextView(view.getContext());
                        textView.setText("No Assets");
                        textView.setPadding(60, 5,60,5);
                        dialog.setView(textView);
                    }

                    dialog.show();
                }
            });
        }
    }
}
