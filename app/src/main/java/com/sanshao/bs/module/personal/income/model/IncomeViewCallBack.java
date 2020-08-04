package com.sanshao.bs.module.personal.income.model;

import com.exam.commonbiz.base.BaseViewCallBack;
import com.sanshao.bs.module.personal.income.bean.IncomeBean;

public interface IncomeViewCallBack extends BaseViewCallBack {

   void requestIncomeInfoSucc(IncomeBean bean);

   void requestIncomeInfoFail(String msg);

}
