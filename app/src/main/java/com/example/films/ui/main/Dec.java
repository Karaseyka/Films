package com.example.films.ui.main;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class Dec extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets (Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.set(0, -parent.getChildAt(0).getHeight(), 0, 0);
    }
}
