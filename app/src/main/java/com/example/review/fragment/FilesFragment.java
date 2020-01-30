package com.example.review.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.review.R;
import com.example.review.api.model.FileContent;
import com.example.review.api.service.GithubService;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class FilesFragment extends Fragment {

    private Stack<String> filePathStack;
    private GithubService mService;
    private FileAdapter mAdapter;
    private String repoFullName;
    private String accessToken;
    private String mCurrentPath;
    private boolean canResponse;


    private enum RefreshType{
        GO_BACK, GO_IN, REFRESH
    }

    private class FileViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout mFileLinearLayout;
        private TextView mFilenameText;
        private ImageView mFileImageView;

        public FileViewHolder(@NonNull View view){
            super(view);

            mFileLinearLayout = view.findViewById(R.id.fileLinearLayout);
            mFilenameText = view.findViewById(R.id.fileNameText);
            mFileImageView = view.findViewById(R.id.fileImageView);
        }

        public void bind(final FileContent content){
            if(content != null){
                mFilenameText.setText(content.getName());

                if(content.getType().equals("dir")){
                    mFileImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_folder,getContext().getTheme()));
                }else{
                    mFileImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_file, getContext().getTheme()));
                }

                mFileLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(FilesFragment.this.canResponse){
                            if(content.getType().equals("dir")) {
                                fetchData("/" + content.getPath(), RefreshType.GO_IN);
                            }
                        }
                    }
                });
            }
        }

    }

    private class FileAdapter extends RecyclerView.Adapter<FileViewHolder>{

        private List<FileContent> files;

        public FileAdapter(){
            super();
            files = new ArrayList<>();
        }

        @NonNull
        @Override
        public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FileViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.file_view_holder, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
            holder.bind(files.get(position));
        }

        @Override
        public int getItemCount() {
            return files.size();
        }

        public void setFiles(List<FileContent> files){
            this.files = files;
        }
    }


    public FilesFragment(GithubService service, String repoFullName){
        super();
        mService = service;
        filePathStack = new Stack<>();
        mCurrentPath = "";
        this.repoFullName = repoFullName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        accessToken = getActivity().getSharedPreferences("loginStat", Context.MODE_PRIVATE).getString("accessToken","");

        View view = inflater.inflate(R.layout.fragment_files, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.fileRecyclerView);
        TextView goBackTextView = view.findViewById(R.id.goBackText);
        mAdapter = new FileAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        goBackTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(canResponse){
                    if(filePathStack.empty()){
                        Toast.makeText(getContext(), "root", Toast.LENGTH_SHORT).show();
                    }else {
                        fetchData(filePathStack.peek(), RefreshType.GO_BACK);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData(mCurrentPath, RefreshType.REFRESH);
    }

    private void fetchData(final String filePath, final RefreshType type){

        canResponse = false;

        Call<List<FileContent>> call = mService.getContentsOfRepo(repoFullName, filePath, accessToken);
        call.enqueue(new Callback<List<FileContent>>() {
            @Override
            public void onResponse(Call<List<FileContent>> call, Response<List<FileContent>> response) {
                if(response.isSuccessful() && response.body() != null){
                    if(type == RefreshType.GO_IN) {
                        filePathStack.push(mCurrentPath);
                        mCurrentPath = filePath;
                    }else if(type == RefreshType.GO_BACK){
                        mCurrentPath = filePathStack.pop();
                    }
                    mAdapter.setFiles(response.body());
                    mAdapter.notifyDataSetChanged();
                }

                canResponse = true;
            }

            @Override
            public void onFailure(Call<List<FileContent>> call, Throwable t) {
                canResponse = true;
            }
        });
    }
}