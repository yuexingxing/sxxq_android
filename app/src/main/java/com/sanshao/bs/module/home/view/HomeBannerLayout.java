package com.sanshao.bs.module.home.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.exam.commonbiz.util.ContainerUtil;
import com.exam.commonbiz.util.ScreenUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.module.home.model.BannerInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Author yuexingxing
 * @time 2020/6/15
 */
public class HomeBannerLayout extends LinearLayout {
    private static final double IMAGE_RATIO_BANNER = 3;

    private HomeBanner mHomeBanner;
    private LinearLayout mLinearLayout;
    private ArrayList<ImageView> mDotList = new ArrayList<>();
    private OnBannerClick mOnBannerClick;

    public interface OnBannerClick{
        void onBannerClick(BannerInfo bannerInfo);
    }

    public void setOnBannerClick(OnBannerClick onBannerClick){
        mOnBannerClick = onBannerClick;
    }

    public HomeBannerLayout(Context context) {
        super(context);
    }

    public HomeBannerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        boolean autoScroll;
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_home_banner, this);
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.HomeBannerLayout, 0, 0);
        try {
            autoScroll = ta.getBoolean(R.styleable.HomeBannerLayout_HomeBannerLayout_autoScroll, true);
        } finally {
            ta.recycle();
        }
        mHomeBanner = rootView.findViewById(R.id.home_banner);
        mHomeBanner.setAutoScroll(autoScroll);
        ViewGroup.LayoutParams params = mHomeBanner.getLayoutParams();
        params.width = ScreenUtil.getScreenSize(getContext())[0];
        params.height = ScreenUtil.dp2px(getContext(), 200);
//        params.height = (int) ((params.width - ScreenUtil.dp2px(getContext(), 30)) / IMAGE_RATIO_BANNER);
        mLinearLayout = rootView.findViewById(R.id.ll_view);
    }

    public HomeBannerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *
     * @param list
     */
    public void setData(List<BannerInfo> list) {
        if (ContainerUtil.isEmpty(list)) {
            return;
        }
        mDotList.clear();
        mLinearLayout.removeAllViews();
        if (list.size() > 1) {
            addDots(list);
        } else {
            mLinearLayout.setVisibility(View.GONE);
        }
        mHomeBanner.setData(list, new BannerClick());
        mHomeBanner.setScrollSpeed(mHomeBanner);
    }

    public void pauseBanner(){
        mHomeBanner.pauseBanner();
    }

    private void addDots(List<BannerInfo> list) {
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(getContext());

            int position = mHomeBanner.getCurrentItem() % list.size();
            if (i == position) {
                imageView.setImageResource(R.drawable.dots_focus);
            } else {
                imageView.setImageResource(R.drawable.dots_normal);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtil.dp2px(getContext(), 19),
                    ScreenUtil.dp2px(getContext(), 3));
            params.setMargins(ScreenUtil.dp2px(getContext(), 2), 0, ScreenUtil.dp2px(getContext(), 3), 0);
            mLinearLayout.addView(imageView, params);
            mDotList.add(imageView);
            mLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    private class BannerClick implements HomeBanner.OnPageClickListener {

        private long lastClickTime = 0;

        @Override
        public void onPageClick(BannerInfo info) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - lastClickTime > 500) {
                lastClickTime = currentTime;
            }
            if (mOnBannerClick != null){
                mOnBannerClick.onBannerClick(info);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (mDotList.size() > 0) {
                for (int i = 0; i < mDotList.size(); i++) {
                    ImageView imageView =  mDotList.get(i);
                    ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                    if (position % mDotList.size() == i) {
                        layoutParams.width =  ScreenUtil.dp2px(getContext(), 19);
                        imageView.setImageResource(R.drawable.dots_focus);
                    } else {
                        layoutParams.width =  ScreenUtil.dp2px(getContext(), 3);
                        imageView.setImageResource(R.drawable.dots_normal);
                    }
                    imageView.setLayoutParams(layoutParams);
                }
            }
        }
    }
}
