package com.sanshao.bs.module.home.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.exam.commonbiz.util.ScreenUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sanshao.bs.R;
import com.sanshao.bs.module.home.model.BannerInfo;
import com.sanshao.bs.module.shoppingcenter.bean.VideoInfo;
import com.sanshao.bs.module.shoppingcenter.widget.VideoPlayLayout;
import com.sanshao.bs.util.GlideUtil;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @Author yuexingxing
 * @time 2020/6/15
 */
public class HomeBanner extends ViewPager {
    private static final String TAG = HomeBanner.class.getSimpleName();
    private MyAdapter mAdapter;
    private Handler mHandler;
    private Runnable mRunnable;
    private int time = 5000;
    private boolean mAutoScroll = true;
    private int mRadius = 10;
    private int mLeftMargin = 12;

    public HomeBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public void setData(List<BannerInfo> bannerList, OnPageClickListener clickListener) {
        if (mAdapter == null || isDataChanged(mAdapter.getData(), bannerList)) {
            mAdapter = new MyAdapter(getContext(), bannerList);
            onPageClickListener = clickListener;
            setAdapter(mAdapter);
            if (mAutoScroll) {
                setCurrentItem(bannerList.size() * 10000);
            } else {
                setOffscreenPageLimit(bannerList.size());
            }
        }

        if (mAutoScroll) {
            mHandler.removeCallbacksAndMessages(null);
            if (bannerList != null && bannerList.size() > 1) {
                mHandler.postDelayed(mRunnable, time);
            }
        } else {
            setCurrentItem(0);
            if (onPageClickListener != null) {
                onPageClickListener.onPageSelected(0);
            }
        }
    }

    public List<BannerInfo> getData() {
        if (mAdapter != null) {
            return mAdapter.getData();
        }
        return null;
    }

    public void setRadius(int radius) {
        mRadius = radius;
    }

    public void setLeftMargin(int leftMargin) {
        mLeftMargin = leftMargin;
    }

    private boolean isDataChanged(List<BannerInfo> old, List<BannerInfo> newData) {
        try {
            if (newData == null || newData.isEmpty()) {
                return false;
            } else if (old == null || old.isEmpty()) {
                return true;
            } else if (old.size() != newData.size()) {
                return true;
            } else {
                for (int i = 0; i < old.size(); i++) {
                    BannerInfo oldInfo = old.get(i);
                    if (i < newData.size()) {
                        BannerInfo newInfo = newData.get(i);
                        if (!oldInfo.equals(newInfo)) {
                            return true;
                        }
                    } else {
                        return true;
                    }

                }
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "isDataChanged method");
            return true;
        }
    }

    private void init() {
        mHandler = new Handler(Looper.getMainLooper());
        setPageTransformer(true, new TranslatePageTransformer());

        mRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = getCurrentItem();
                if (mAdapter == null) {
                    return;
                }
                if (currentItem == mAdapter.getCount() - 1) {
                    setCurrentItem(0);
                } else {
                    setCurrentItem(currentItem + 1);
                }
                if (mHandler != null && mAutoScroll) {
                    mHandler.postDelayed(mRunnable, time);
                }
            }
        };

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (onPageClickListener != null) {
                    onPageClickListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setAutoScroll(boolean autoScroll) {
        this.mAutoScroll = autoScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                if (mAutoScroll) {
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler.postDelayed(mRunnable, time);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mHandler.removeCallbacksAndMessages(null);
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, true);
    }

    public void pauseBanner() {
        mHandler.removeCallbacksAndMessages(null);
    }

    public void resumeBanner() {
        if (mAutoScroll) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler.postDelayed(mRunnable, time);
        }
    }

    public OnPageClickListener onPageClickListener;

    public class MyAdapter extends PagerAdapter implements OnClickListener {

        private Context mContext;
        private List<BannerInfo> mList;

        public MyAdapter(Context context, List<BannerInfo> list) {
            mList = list;
            mContext = context;
        }

        @Override
        public int getCount() {
            if (mList == null || mList.isEmpty()) {
                return 0;
            } else if (!mAutoScroll) {
                return mList.size();
            } else {
                return Integer.MAX_VALUE;

            }
        }

        public BannerInfo getItem(int position) {
            int pos = position % mList.size();
            if (pos >= 0 && pos < mList.size()) {
                BannerInfo info = mList.get(pos);
                return info;
            }
            return null;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int pos = position % mList.size();
            if (pos >= 0 && pos < mList.size()) {
                BannerInfo bannerInfo = mList.get(pos);
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_banner, container, false);
                view.setTag(bannerInfo);
                view.setOnClickListener(this);
                RoundedImageView bannerImage = view.findViewById(R.id.banner_image);
                bannerImage.setCornerRadius(ScreenUtil.dp2px(getContext(), mRadius));

                int leftMargin = ScreenUtil.dp2px(getContext(), mLeftMargin);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(bannerImage.getLayoutParams());
                layoutParams.setMargins(leftMargin, 0, leftMargin, 0);
                bannerImage.setLayoutParams(layoutParams);

                VideoPlayLayout videoPlayLayout = view.findViewById(R.id.video_play_layout);
                if (!TextUtils.isEmpty(bannerInfo.videoUrl)) {
                    bannerImage.setVisibility(GONE);
                    videoPlayLayout.setVisibility(VISIBLE);
                    VideoInfo videoInfo = new VideoInfo();
                    videoInfo.video = bannerInfo.videoUrl;
                    videoInfo.img = bannerInfo.videoPic;
                    videoPlayLayout.setVideoInfo(videoInfo);
                } else {
                    videoPlayLayout.setVisibility(GONE);
                    bannerImage.setVisibility(VISIBLE);
                    GlideUtil.loadImage(bannerInfo.artitag_url, bannerImage);
                }
                container.addView(view);
                return view;
            } else {
                return null;
            }

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public List<BannerInfo> getData() {
            return mList;
        }

        @Override
        public void onClick(View v) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler.postDelayed(mRunnable, time);

            if (v.getTag() instanceof BannerInfo) {
                BannerInfo info = (BannerInfo) v.getTag();
                if (onPageClickListener != null) {
                    onPageClickListener.onPageClick(info);
                }
            }
        }
    }

    public interface OnPageClickListener {
        void onPageClick(BannerInfo info);

        void onPageSelected(int position);
    }

    public class TranslatePageTransformer implements PageTransformer {

        @Override
        public void transformPage(View page, float position) {

            if (position < -1) {
                position = -1;
            } else if (position > 1) {
                position = 1;
            }

            final float normalizedposition = Math.abs(Math.abs(position) - 1);
            page.setAlpha(normalizedposition);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                page.getParent().requestLayout();
            }
        }
    }

    public void setScrollSpeed(ViewPager mViewPager) {
        try {
            Class clazz = Class.forName("androidx.viewpager.widget.ViewPager");
            Field f = clazz.getDeclaredField("mScroller");
            FixedSpeedScroller fixedSpeedScroller = new FixedSpeedScroller(getContext(), new LinearOutSlowInInterpolator());
            fixedSpeedScroller.setmDuration(1000);
            f.setAccessible(true);
            f.set(mViewPager, fixedSpeedScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class FixedSpeedScroller extends Scroller {
        private int mDuration = 1000;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setmDuration(int time) {
            mDuration = time;
        }

        public int getmDuration() {
            return mDuration;
        }
    }
}
