package com.example.review.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.review.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    private final static String AUTHOR_URL = "https://github.com/login/oauth/authorize";
    private final static String CLIENT_ID = "74c29ed1c122fa9547d1";
    private final static String CLIENT_SECRET = "671f67129c2d67417bac90200a1ee2f0ad66fb62";
    private final static String CALLBACK_URI = "androidgithub://callback";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        final EditText emailText = view.findViewById(R.id.loginEmail);
        final EditText passwordText = view.findViewById(R.id.loginPassword);
        Button loginButton = view.findViewById(R.id.loginButtonFragment);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(AUTHOR_URL + "?client_id=" + CLIENT_ID + "&scope=repo&redirect_uri=" + CALLBACK_URI));
                startActivity(intent);
            }
        });

        return view;
    }
}
