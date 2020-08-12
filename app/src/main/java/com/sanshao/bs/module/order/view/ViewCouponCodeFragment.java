package com.sanshao.bs.module.order.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.exam.commonbiz.base.BaseFragment;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.FragmentViewCouponcodeBinding;
import com.sanshao.bs.module.order.view.adapter.TabFragmentPagerAdapter;
import com.sanshao.bs.module.order.viewmodel.OrderStatusViewModel;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.util.Constants;
import com.sanshao.bs.widget.ViewPagerScroller;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看券码-viewpager
 *
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class ViewCouponCodeFragment extends BaseFragment<OrderStatusViewModel, FragmentViewCouponcodeBinding> {

    private TabFragmentPagerAdapter adapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private GoodsDetailInfo mGoodsDetailInfo;

    public static ViewCouponCodeFragment newInstance(GoodsDetailInfo goodsDetailInfo) {
        ViewCouponCodeFragment fragment = new ViewCouponCodeFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.OPT_DATA, goodsDetailInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view_couponcode;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {

        binding.llContent.setVisibility(View.GONE);
        binding.ivLeft.setOnClickListener(v -> {
            setCurrentItem(0);
        });
        binding.ivRight.setOnClickListener(v -> {
            setCurrentItem(1);
        });

        mGoodsDetailInfo = (GoodsDetailInfo) getArguments().getSerializable(Constants.OPT_DATA);
        for (int i = 0; i < mGoodsDetailInfo.write_off.size(); i++) {
            mFragmentList.add(ViewCouponCodeDetailFragment.newInstance(mGoodsDetailInfo.write_off.get(i)));
        }
        binding.tvTitle.setText(mGoodsDetailInfo.sarti_name);
        Glide.with(SSApplication.app).load(mGoodsDetailInfo.thumbnail_img).into(binding.ivIcon);
        initViewPager();

        binding.tvQrCode.setText("券码：" + mGoodsDetailInfo.write_off.get(0).code);
        setCurrentItem(0);
        if (mGoodsDetailInfo.write_off.size() == 1) {
            binding.ivRight.setImageResource(R.drawable.but_rightrow_hasbeenused);
        }
    }

    private void initViewPager() {
        ViewPagerScroller scroller = new ViewPagerScroller(context);
        scroller.setScrollDuration(1500);
        scroller.initViewPagerScroll(binding.viewPager);
        adapter = new TabFragmentPagerAdapter(getChildFragmentManager(), mFragmentList);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setScrollable(false);
        binding.viewPager.setOffscreenPageLimit(mFragmentList.size());
        binding.viewPager.setCurrentItem(0);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.tvQrCode.setText("券码：234832048-" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * @param type 0-减 1-加
     */
    private void setCurrentItem(int type) {
        int currentItem = binding.viewPager.getCurrentItem();
        if (type == 0) {
            if (currentItem == 0) {
                return;
            }
            binding.viewPager.setCurrentItem(currentItem - 1);
        } else {
            if (currentItem > mFragmentList.size() - 1) {
                return;
            }
            binding.viewPager.setCurrentItem(currentItem + 1);
        }

        if (binding.viewPager.getCurrentItem() == 0) {
            binding.ivLeft.setImageResource(R.drawable.but_leftrow_hasbeenused);
        } else {
            binding.ivLeft.setImageResource(R.drawable.btu_lefttrow_tobeused);
        }

        if (binding.viewPager.getCurrentItem() == mFragmentList.size() - 1) {
            binding.ivRight.setImageResource(R.drawable.but_rightrow_hasbeenused);
        } else {
            binding.ivRight.setImageResource(R.drawable.btu_rightrow);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        if (adapter != null) {
            adapter.notify();
        }
        return super.onCreateView(inflater, container, state);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}