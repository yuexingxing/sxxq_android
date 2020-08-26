package cn.sanshaoxingqiu.ssbm.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class NoSlidingViewPager extends ViewPager {

    private boolean slide = false;// false 禁止ViewPager左右滑动。

    public NoSlidingViewPager(@NonNull Context context) {
        super(context);
    }

    public NoSlidingViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public void setScrollable(boolean slide) {
        this.slide = slide;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return slide;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return slide;
    }

}
