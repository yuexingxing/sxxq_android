package com.sanshao.bs.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exam.commonbiz.util.ScreenUtil;
import com.sanshao.bs.R;

/**
 * 设置等页面条状控制或显示信息的控件
 */
public class LineControllerView extends LinearLayout {

    private String mName;
    private boolean mIsBottom;
    private String mContent;
    private boolean mIsJump;

    private TextView mNameText;
    private TextView mContentText;
    private ImageView mNavArrowView;

    public LineControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.line_controller_view, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LineControllerView, 0, 0);
        try {
            mName = ta.getString(R.styleable.LineControllerView_name);
            mContent = ta.getString(R.styleable.LineControllerView_subject);
            mIsBottom = ta.getBoolean(R.styleable.LineControllerView_isBottom, false);
            mIsJump = ta.getBoolean(R.styleable.LineControllerView_canNav, false);
            setUpView();
        } finally {
            ta.recycle();
        }
    }

    private void setUpView() {
        mNameText = findViewById(R.id.name);
        mNameText.setText(mName);
        mContentText = findViewById(R.id.content);
        mContentText.setText(mContent);
        View bottomLine = findViewById(R.id.bottomLine);
        bottomLine.setVisibility(mIsBottom ? VISIBLE : GONE);
        mNavArrowView = findViewById(R.id.rightArrow);
        mNavArrowView.setVisibility(mIsJump ? VISIBLE : GONE);
    }

    /**
     * 获取内容
     */
    public String getContent() {
        return mContentText.getText().toString();
    }

    /**
     * 设置文字内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.mContent = content;
        mContentText.setText(content);
    }

    public void setSingleLine(boolean singleLine) {
        mContentText.setSingleLine(singleLine);
    }

    /**
     * 设置是否可以跳转
     *
     * @param canNav 是否可以跳转
     */
    public void setCanNav(boolean canNav) {
        this.mIsJump = canNav;
        mNavArrowView.setVisibility(canNav ? VISIBLE : GONE);
        if (canNav) {
            ViewGroup.LayoutParams params = mContentText.getLayoutParams();
            params.width = ScreenUtil.px2dp(getContext(), 120);
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            mContentText.setLayoutParams(params);
            mContentText.setTextIsSelectable(false);
        } else {
            ViewGroup.LayoutParams params = mContentText.getLayoutParams();
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            mContentText.setLayoutParams(params);
            mContentText.setTextIsSelectable(true);
        }
    }
}
