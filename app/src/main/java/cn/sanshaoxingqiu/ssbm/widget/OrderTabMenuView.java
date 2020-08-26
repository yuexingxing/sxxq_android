package cn.sanshaoxingqiu.ssbm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.sanshaoxingqiu.ssbm.R;

/**
 * tabMenu栏自定义控件
 *
 * @Author yuexingxing
 * @time 2020/7/1
 */
public class OrderTabMenuView extends LinearLayout {

    private String mTitle;
    private boolean isShowLine;

    private TextView mTvName;
    private View mViewLine;

    public OrderTabMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_order_tab_menu_view, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.OrderTabMenuView, 0, 0);
        try {
            mTitle = typedArray.getString(R.styleable.OrderTabMenuView_title);
            isShowLine = typedArray.getBoolean(R.styleable.OrderTabMenuView_isShowLine, false);
            initView();
        } finally {
            typedArray.recycle();
        }
    }

    private void initView() {
        mTvName = findViewById(R.id.tv_title);
        mTvName.setText(mTitle);
        mViewLine = findViewById(R.id.view_line);
        if (isShowLine) {
            mViewLine.setVisibility(VISIBLE);
        } else {
            mViewLine.setVisibility(INVISIBLE);
        }
    }

    public void setName(String content) {
        mTvName.setText(content);
    }

    public void setLineVisible(int visible) {
        mViewLine.setVisibility(visible);
    }
}
