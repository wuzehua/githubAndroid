package com.example.review.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.VelocityTracker;
import android.widget.FrameLayout;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NaturalRecyclerView extends FrameLayout {

    private final static String TAG = "NaturalRecyclerView";


    private OverScroller mOverScroller;
    private RecyclerView mRecyclerView;
    private VelocityTracker mVelocityTracker;
    private float acceleration;
    private float maxScrollRatio;



    public NaturalRecyclerView(@NonNull Context context) {
        super(context);
    }

    public NaturalRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
