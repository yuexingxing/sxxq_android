package com.sanshao.bs.module.personal.income.view;

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
public class IncomeFragment extends BaseFragment<BaseViewModel, FragmentIncomeBinding> {

    public static IncomeFragment newInstance() {
        IncomeFragment fragment = new IncomeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_income;
    }

    @Override
    public void initData() {

    }


}