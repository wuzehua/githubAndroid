package com.example.review.ui.rv;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.review.R;
import com.example.review.api.model.Asset;
import com.example.review.utils.PermissionUtils;

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

    public void bind(final Asset data, Context context){
        if(data != null){

            mFilenameText.setText(data.getName());
            mFileLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(PermissionUtils.isPermissionsReady(view.getContext(), PermissionUtils.permissions)){
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(data.getDownloadUrl()));
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                        request.setTitle(data.getName());
                        request.setDestinationInExternalFilesDir(view.getContext(), Environment.DIRECTORY_DOWNLOADS, "");

                        DownloadManager manager = (DownloadManager) view.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                        if(manager != null) {
                            manager.enqueue(request);
                            Toast.makeText(view.getContext(), "Start to download " + data.getName(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(view.getContext(), "Start downloading failed", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(view.getContext(), "Writing external storage is not permitted", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
