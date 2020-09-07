package cn.sanshaoxingqiu.ssbm.module.personal.income.view;

import android.text.TextUtils;

import com.exam.commonbiz.base.BaseFragment;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.FragmentRankingBinding;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.RankinglistBean;
import cn.sanshaoxingqiu.ssbm.module.personal.income.model.RankinglistViewCallBack;
import cn.sanshaoxingqiu.ssbm.module.personal.income.viewmodel.RankinglistViewModel;
import com.exam.commonbiz.util.ToastUtil;

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
