package com.sanshao.bs.module.personal.income.view;

import android.text.TextUtils;
import com.exam.commonbiz.base.BaseFragment;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.FragmentRankingBinding;
import com.sanshao.bs.module.personal.income.bean.RankinglistBean;
import com.sanshao.bs.module.personal.income.model.RankinglistViewCallBack;
import com.sanshao.bs.module.personal.income.viewmodel.RankinglistViewModel;
import com.sanshao.bs.util.ToastUtil;

public class RankinglistFragment extends BaseFragment<RankinglistViewModel, FragmentRankingBinding> implements RankinglistViewCallBack {

    public static RankinglistFragment newInstance() {
        RankinglistFragment fragment = new RankinglistFragment();
        return fragment;
    }

    @Override
    public void initData() {
        mViewModel.setCallBack(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranking;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void requestRankinglistSucc(RankinglistBean bean) {

    }

    @Override
    public void requestRankinglistFail(String msg) {
        if (TextUtils.isEmpty(msg)) {
            msg = getString(R.string.request_data_fail);
        }
        ToastUtil.showShortToast(msg);
    }
}
