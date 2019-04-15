package com.geappliances.test.sunny.search;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @file ListItemDecoration.java
 * @date 20/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class ListItemDecoration extends RecyclerView.ItemDecoration {

    private int divHeigh = 0;

    public ListItemDecoration(int divHeigh){
        this.divHeigh = divHeigh;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.top = divHeigh;
    }
}
