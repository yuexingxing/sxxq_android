package com.sanshao.bs.module.personal.income.model;

import com.exam.commonbiz.base.BaseViewCallBack;
import com.sanshao.bs.module.personal.income.bean.RankinglistBean;

public interface RankinglistViewCallBack extends BaseViewCallBack {

    void requestRankinglistSucc(RankinglistBean bean);

    void requestRankinglistFail(String msg);
}
