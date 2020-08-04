package com.sanshao.bs.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sanshao.bs.R;

/**
 * recyclerview空数据/网络错误页面封装
 *
 * @Author yuexingxing
 * @time 2020/7/31
 */
public class EmptyLayout extends FrameLayout {

    private View mEmptyView;
    private View mBindView;
    private View mErrorView;
    private TextView mBtnReset;
    private TextView mEmptyText;
    private ImageView ivImg;

    public EmptyLayout(Context context) {
        this(context, null);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //居中
        params.gravity = Gravity.CENTER;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EmptyLayout, 0, 0);

        //数据为空时的布局
        int emptyLayout = ta.getResourceId(R.styleable.EmptyLayout_elEmptyLayout, R.layout.layout_empty);
        mEmptyView = View.inflate(context, emptyLayout, null);
        mEmptyText = mEmptyView.findViewById(R.id.tv_title);
        ivImg = mEmptyView.findViewById(R.id.iv_icon);
        addView(mEmptyView, params);

        //错误时的布局
        int errorLayout = ta.getResourceId(R.styleable.EmptyLayout_elErrorLayout, R.layout.layout_error);
        mErrorView = View.inflate(context, errorLayout, null);
        mBtnReset = (TextView) mErrorView.findViewById(R.id.tv_title);
        addView(mErrorView, params);

        //全部隐藏
        setGone();
    }

    /**
     * 全部隐藏
     */
    public void setGone() {
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
    }

    public void showError() {
        showError(null);
    }

    public void showError(String text) {
        if (mBindView != null) mBindView.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(text)) mBtnReset.setText(text);
        setGone();
        mErrorView.setVisibility(View.VISIBLE);
    }

    public void showEmpty(String text, int imaSrc) {
        if (mBindView != null) mBindView.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(text)) mEmptyText.setText(text);
        ivImg.setBackgroundResource(imaSrc);
        setGone();
        mEmptyView.setVisibility(View.VISIBLE);
    }

    public void showSuccess() {
        if (mBindView != null) mBindView.setVisibility(View.VISIBLE);
        setGone();
    }

    public void bindView(View view) {
        mBindView = view;
    }

    public void setOnButtonClick(OnClickListener listener) {
        mBtnReset.setOnClickListener(listener);
    }
}
