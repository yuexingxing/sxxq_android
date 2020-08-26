package cn.sanshaoxingqiu.ssbm.module.home.view;

import android.view.View;

import com.exam.commonbiz.base.BaseFragment;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.HomeFragmentBinding;
import cn.sanshaoxingqiu.ssbm.module.home.viewmodel.HomeViewModel;

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
    protected void loadData() {

    }

    @Override
    public void initData() {

        binding.titleBar.getLeftView().setVisibility(View.INVISIBLE);
    }
}