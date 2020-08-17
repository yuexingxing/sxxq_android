package com.sanshao.bs.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exam.commonbiz.util.ScreenUtil;
import com.sanshao.bs.R;

/**
 * 个人中心我的订单自定义控件
 *
 * @Author yuexingxing
 * @time 2020/7/13
 */
public class PersonalOrderMenuView extends LinearLayout {

    private TextView mTvName;
    private ImageView mImgIcon;
    private String mName;
    private int mIcon;
    private int mUnReadNum;
    private TextView mTvMessageNum;

    public PersonalOrderMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_layout_personal_order_menu, this);
        mTvName = findViewById(R.id.tv_name);
        mImgIcon = findViewById(R.id.iv_icon);
        mTvMessageNum = findViewById(R.id.tv_message_num);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PersonalOrderMenuView, 0, 0);
        try {
            mName = ta.getString(R.styleable.PersonalOrderMenuView_PersonalOrderMenuView_name);
            mIcon = ta.getResourceId(R.styleable.PersonalOrderMenuView_PersonalOrderMenuView_icon, R.drawable.beautiful);
            mUnReadNum = ta.getInteger(R.styleable.PersonalOrderMenuView_PersonalOrderMenuView_num, 0);
            initView();
        } finally {
            ta.recycle();
        }
    }

    public ImageView getImageView() {
        return mImgIcon;
    }

    private void initView() {
        mTvName.setText(mName);
        mImgIcon.setImageResource(mIcon);
        mTvMessageNum.setText(mUnReadNum + "");
    }

    public void setUnReadNum(int unReadNum) {
        mTvMessageNum.setText(unReadNum + "");
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mTvMessageNum.getLayoutParams();
        layoutParams.height = ScreenUtil.dp2px(getContext(), 12);
        if (unReadNum <= 0) {
            mTvMessageNum.setVisibility(GONE);
            return;
        } else if (unReadNum < 10) {
            layoutParams.width = ScreenUtil.dp2px(getContext(), 12);
        } else if (unReadNum < 100) {
            layoutParams.width = ScreenUtil.dp2px(getContext(), 18);
            layoutParams.leftMargin = ScreenUtil.dp2px(getContext(), 15);
        } else {
            layoutParams.width = ScreenUtil.dp2px(getContext(), 24);
            layoutParams.leftMargin = ScreenUtil.dp2px(getContext(), 15);
            mTvMessageNum.setText("99+");
        }
        mTvMessageNum.setLayoutParams(layoutParams);
        mTvMessageNum.setVisibility(VISIBLE);
    }
}
