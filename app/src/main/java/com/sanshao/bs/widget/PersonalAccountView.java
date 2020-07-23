package com.sanshao.bs.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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
    private String mContent;
    private int iconId;
    private boolean isShowLine;

    private ImageView mIcon;
    private TextView mTvTitle;
    private TextView mTvContent;
    private ImageView mIconArrow;

    public PersonalAccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_layout_personal_account_view, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PersonalAccountView, 0, 0);
        try {
            mTitle = typedArray.getString(R.styleable.PersonalAccountView_title_pav);
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
        mTvContent = findViewById(R.id.tv_content);
        mTvTitle.setText(mTitle);
        mTvContent.setText(mContent);
        if (iconId != 0) {
            mIcon.setImageResource(iconId);
        } else {
            mIcon.setVisibility(GONE);
        }

        if (isShowLine) {

        }
    }

    public void setName(String content) {
        mTvTitle.setText(content);
    }
}
