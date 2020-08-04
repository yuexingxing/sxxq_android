package com.sanshao.bs.module.personal.income.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.personal.income.bean.RankinglistBean;
import com.sanshao.bs.module.personal.income.model.RankinglistModel;
import com.sanshao.bs.module.personal.income.model.RankinglistViewCallBack;

public class RankinglistViewModel extends BaseViewModel {

    private RankinglistViewCallBack callBack;

    public void setCallBack(RankinglistViewCallBack callBack) {
        this.callBack = callBack;
    }

    public void requestRankinglist() {
        RankinglistModel.requestRankinglist(new OnLoadListener<RankinglistBean>() {
            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<RankinglistBean> t) {
                if (callBack != null) {
                    callBack.requestRankinglistSucc(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                if (callBack != null) {
                    callBack.requestRankinglistFail(errMsg);
                }
            }
        });
    }
}
