package cn.sanshaoxingqiu.ssbm.module.personal.income.view;

import android.text.TextUtils;
import android.view.View;

import com.exam.commonbiz.base.BaseFragment;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.FragmentIncomeBinding;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeBean;
import cn.sanshaoxingqiu.ssbm.module.personal.income.model.IncomeViewCallBack;
import cn.sanshaoxingqiu.ssbm.module.personal.income.viewmodel.IncomeViewModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.ExerciseActivity;

import com.exam.commonbiz.base.EmptyWebViewActivity;
import com.exam.commonbiz.util.Constants;
import com.exam.commonbiz.util.ToastUtil;

/**
 * 进账主页
 *
 * @Author yuexingxing
 * @time 2020/10/12
 */
public class IncomeFrament extends BaseFragment<IncomeViewModel, FragmentIncomeBinding> implements IncomeViewCallBack, View.OnClickListener {

    public static IncomeFrament newInstance() {
        IncomeFrament fragment = new IncomeFrament();
        return fragment;
    }

    @Override
    public void initData() {

        binding.incomeRule.setOnClickListener(this);
        binding.viewWithdrawal.setOnClickListener(this);
        binding.viewMyJimei.setOnClickListener(this);
        binding.viewIncomeRecord.setOnClickListener(this);
        binding.viewWithdrawalRecord.setOnClickListener(this);

        mViewModel.setCallBack(this);
        mViewModel.requestIncomeInfo();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_income;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void requestIncomeInfoSucc(IncomeBean bean) {
        if (bean == null) {
            return;
        }
        binding.incomeId.setText("ID：" + (bean.id == null ? "" : bean.id));
        binding.incomeRecommendCode.setText(getString(R.string.income_recommend_code) + (bean.code == null ? "" : bean.code));
        binding.canWithdrawalAmount.setText(bean.canWithdrawalAmount == null ? "" : bean.canWithdrawalAmount);
        binding.totalAmount.setText(getString(R.string.total_amount) + (bean.totalAmount == null ? "" : bean.totalAmount));
        binding.alreadyWithdrawalAmount.setText(getString(R.string.withdrawal_amount) + (bean.alreadyWithdrawalAmount == null ? "" : bean.alreadyWithdrawalAmount));
    }

    @Override
    public void requestIncomeInfoFail(String msg) {
        if (TextUtils.isEmpty(msg)) {
            msg = getString(R.string.request_data_fail);
        }
        ToastUtil.showShortToast(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.income_rule:
                EmptyWebViewActivity.start(context, "", Constants.withdrawalrulesUrl);
                break;
            case R.id.view_withdrawal:
                WithdrawActivity.start(context);
                break;
            case R.id.view_my_jimei:
                break;
            case R.id.view_income_record:
                break;
            case R.id.view_withdrawal_record:
                break;
        }
    }
}
