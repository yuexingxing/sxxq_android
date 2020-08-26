package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.exam.commonbiz.util.ContainerUtil;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.order.view.adapter.TabFragmentPagerAdapter;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情-套餐自定义控件
 *
 * @Author yuexingxing
 * @time 2020/7/1
 */
public class TryMatchingView extends LinearLayout {

    private ViewPager mViewPager;
    private TabFragmentPagerAdapter adapter;
    private List<Fragment> mFragmentList = new ArrayList<>();

    public TryMatchingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_layout_try_matching, this);
        initView();
    }

    private void initView() {
        mViewPager = findViewById(R.id.view_pager_try);
    }

    public void initViewPager(FragmentManager fragmentManager) {

        adapter = new TabFragmentPagerAdapter(fragmentManager, mFragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        mViewPager.setCurrentItem(0);
    }

    public void setData(List<GoodsDetailInfo> goodsDetailInfoList) {
        if (ContainerUtil.isEmpty(goodsDetailInfoList)) {
            return;
        }
        for (int i = 0; i < goodsDetailInfoList.size(); i++) {
            GoodsDetailInfo goodsDetailInfo = goodsDetailInfoList.get(i);
            goodsDetailInfo.position = i;
            TryMatchingFragment tryMatchingFragment = TryMatchingFragment.newInstance(goodsDetailInfo);
            mFragmentList.add(tryMatchingFragment);
        }
    }
}
