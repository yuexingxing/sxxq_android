package cn.sanshaoxingqiu.ssbm.module.personal.income.model;

import java.util.List;

import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.BankCardInfo;

public interface IBindBankCardModel {

    void returnBankList(List<BankCardInfo> bankCardInfoList);

    void onBindBankCardSuccess();

    void onBindBankCardFailed();
}
