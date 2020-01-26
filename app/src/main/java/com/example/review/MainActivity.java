package com.example.review;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.RadioGroup;


public class MainActivity extends AppCompatActivity {

    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LoginFragment loginFragment = new LoginFragment();
        final RecyclerFragment recyclerFragment = new RecyclerFragment();

        mCurrentFragment = recyclerFragment;

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainFragmentLayout, recyclerFragment)
                .commit();

        RadioGroup tabs = findViewById(R.id.bottomNavigate);
        tabs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.recyclerTab:{
                        switchFragment(recyclerFragment);
                        break;
                    }
                    case R.id.loginTab:{
                        switchFragment(loginFragment);
                        break;
                    }
                }
            }
        });

    }

    private void switchFragment(Fragment fragment){
        if(mCurrentFragment != fragment){
            if(fragment.isAdded()){
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mCurrentFragment)
                        .show(fragment)
                        .commit();
            }else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(mCurrentFragment)
                        .add(R.id.mainFragmentLayout, fragment)
                        .commit();
            }

            mCurrentFragment = fragment;
        }
    }


}