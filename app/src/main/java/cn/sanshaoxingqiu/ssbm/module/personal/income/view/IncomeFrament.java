package cn.sanshaoxingqiu.ssbm.module.personal.income.view;

import android.view.View;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.base.EmptyWebViewActivity;
import com.exam.commonbiz.util.Constants;

import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.FragmentIncomeBinding;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeBean;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.WithdrawInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.model.IncomeViewCallBack;
import cn.sanshaoxingqiu.ssbm.module.personal.income.viewmodel.IncomeViewModel;

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
        binding.incomeId.setText(getString(R.string.income_recommend_code) + bean.invitation_code);
        binding.canWithdrawalAmount.setText(bean.used_price + "");
        binding.totalAmount.setText(getString(R.string.total_amount) + bean.commission);
        binding.alreadyWithdrawalAmount.setText(getString(R.string.withdrawal_amount) + bean.underway);
    }

    @Override
    public void withdraw() {

    }

    @Override
    public void returnIncomeRecordList(List<IncomeInfo> incomeInfoList) {

    }

    @Override
    public void returnWithdrawRecordList(List<WithdrawInfo> withdrawInfoList) {

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
                IncomeRecordActivity.start(context);
                break;
            case R.id.view_withdrawal_record:
                WithdrawRecordActivity.start(context);
                break;
        }
    }
}
