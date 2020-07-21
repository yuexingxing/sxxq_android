package com.sanshao.bs.module.order.util;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView设置行间距
 *
 * @Author yuexingxing
 * @time 2020/7/1
 */
public class GuessYouLoveSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int lineCount;

    public GuessYouLoveSpaceItemDecoration(int space, int lineCount) {
        this.space = space;
        this.lineCount = lineCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.left = space;
        outRect.bottom = space;
        if (parent.getChildLayoutPosition(view) % lineCount == 0) {
            outRect.left = 0;
            outRect.right = space;
        }
    }
}