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
 * 个人中心我的订单自定义控件
 *
 * @Author yuexingxing
 * @time 2020/7/13
 */
public class PersonalOrderMenuView extends LinearLayout {

    private TextView mTvName;
    private ImageView mImgIcon;
    private TextView mTvNum;
    private String mName;
    private int mIcon;
    private int mUnReadNum;

    public PersonalOrderMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_layout_personal_order_menu, this);
        mTvName = findViewById(R.id.tv_name);
        mImgIcon = findViewById(R.id.iv_icon);
        mTvNum =findViewById(R.id.tv_num);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PersonalOrderMenuView, 0, 0);
        try {
            mName = ta.getString(R.styleable.PersonalOrderMenuView_PersonalOrderMenuView_name);
            mIcon = ta.getResourceId(R.styleable.PersonalOrderMenuView_PersonalOrderMenuView_icon, R.drawable.beautiful);
            mUnReadNum  = ta.getInteger(R.styleable.PersonalOrderMenuView_PersonalOrderMenuView_num, 0);
            initView(context);
        } finally {
            ta.recycle();
        }
    }

    private void initView(Context context) {
        mTvName.setText(mName);
        mImgIcon.setImageResource(mIcon);
        mTvNum.setText(mUnReadNum + "");
    }

    public void setUnReadNum(int unReadNum){
        mTvNum.setText(unReadNum + "");
    }
}
