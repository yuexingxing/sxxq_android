package com.sanshao.bs.module.personal.view;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.FragmentIncomeBinding;

/**
 * 进账
 *
 * @Author yuexingxing
 * @time 2020/7/13
 */
public class RankingFragment extends BaseFragment<BaseViewModel, FragmentIncomeBinding> {

    public static RankingFragment newInstance() {
        RankingFragment fragment = new RankingFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranking;
    }

    @Override
    public void initData() {

    }


}