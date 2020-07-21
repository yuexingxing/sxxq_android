package com.sanshao.bs.module.home.view;

import android.view.View;

import com.exam.commonbiz.base.BaseFragment;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.HomeFragmentBinding;
import com.sanshao.bs.module.home.viewmodel.HomeViewModel;

/**
 * 首页
 *
 * @Author yuexingxing
 * @time 2020/6/12
 */
public class HomeFragment extends BaseFragment<HomeViewModel, HomeFragmentBinding> {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    public void initData() {

        binding.titleBar.getLeftView().setVisibility(View.INVISIBLE);
    }
}