package cn.sanshaoxingqiu.ssbm.module.personal.income.model;

import com.exam.commonbiz.base.BaseViewCallBack;

import java.util.List;

import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeBean;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.WithdrawInfo;

public interface IncomeViewCallBack extends BaseViewCallBack {

    void requestIncomeInfoSucc(IncomeBean bean);

    void withdraw();

    void returnIncomeRecordList(List<IncomeInfo> incomeInfoList);

    void returnWithdrawRecordList(List<WithdrawInfo> withdrawInfoList);
}
