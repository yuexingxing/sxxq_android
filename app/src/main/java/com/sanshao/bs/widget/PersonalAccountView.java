package com.sanshao.bs.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sanshao.bs.R;

/**
 * tabMenu栏自定义控件
 *
 * @Author yuexingxing
 * @time 2020/7/1
 */
public class PersonalAccountView extends LinearLayout {

    private String mTitle;
    private String mTitle2;
    private String mContent;
    private int iconId;
    private boolean isShowLine;

    private ImageView mIcon;
    private TextView mTvTitle;
    private TextView mTvTitle2;
    private TextView mTvContent;
    private View mViewLineBottom;
    private ImageView mIconArrow;

    public PersonalAccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_layout_personal_account_view, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PersonalAccountView, 0, 0);
        try {
            mTitle = typedArray.getString(R.styleable.PersonalAccountView_title_pav);
            mTitle2 = typedArray.getString(R.styleable.PersonalAccountView_title2_pav);
            mContent = typedArray.getString(R.styleable.PersonalAccountView_content_pav);
            isShowLine = typedArray.getBoolean(R.styleable.PersonalAccountView_isShowLine_pav, false);
            iconId = typedArray.getResourceId(R.styleable.PersonalAccountView_icon_pav, 0);
            initView();
        } finally {
            typedArray.recycle();
        }
    }

    private void initView() {
        mIcon = findViewById(R.id.iv_icon);
        mTvTitle = findViewById(R.id.tv_title);
        mTvTitle2 = findViewById(R.id.tv_title2);
        mTvContent = findViewById(R.id.tv_content);
        mViewLineBottom = findViewById(R.id.view_line_bottom);
        mTvTitle.setText(mTitle);
        mTvTitle2.setText(mTitle2);
        mTvContent.setText(mContent);
        if (iconId != 0) {
            mIcon.setImageResource(iconId);
        } else {
            mIcon.setVisibility(GONE);
        }

        if (isShowLine) {
            mViewLineBottom.setVisibility(VISIBLE);
        } else {
            mViewLineBottom.setVisibility(GONE);
        }

        if (TextUtils.isEmpty(mTitle2)) {
            mTvTitle2.setVisibility(GONE);
        } else {
            mTvTitle2.setVisibility(VISIBLE);
        }
    }

    public void setName(String content) {
        mTvTitle.setText(content);
    }

    public void setContent(String content) {
        mTvContent.setText(content);
    }
}
