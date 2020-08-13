package com.exam.commonbiz.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class CustomerGridView extends GridView {

    public CustomerGridView(Context context) {
        super(context);
    }

    public CustomerGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
