package com.sanshao.bs.module.shoppingcenter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.sanshao.bs.R;
import com.sanshao.bs.module.order.view.ViewCouponCodeFragment;
import com.sanshao.bs.module.order.view.adapter.TabFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情-套餐自定义控件
 *
 * @Author yuexingxing
 * @time 2020/7/1
 */
public class SetMealGoodsDetailView extends LinearLayout {

    private ViewPager mViewPager;
    private TabFragmentPagerAdapter adapter;
    private List<Fragment> mFragmentList = new ArrayList<>();

    public SetMealGoodsDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_layout_setmeal_goods_detail, this);
        initView();
    }

    private void initView() {
        mViewPager = findViewById(R.id.view_pager);
    }

    public void initViewPager(FragmentManager fragmentManager) {

        adapter = new TabFragmentPagerAdapter(fragmentManager, mFragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(2);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
    }

    public void initData() {

        for (int i = 0; i < 5; i++) {
            mFragmentList.add(ViewCouponCodeFragment.newInstance(i));
        }
    }
}
