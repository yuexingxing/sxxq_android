package com.sanshao.bs.module.personal.income.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.personal.income.bean.IncomeBean;
import com.sanshao.bs.module.personal.income.model.IncomeModel;
import com.sanshao.bs.module.personal.income.model.IncomeViewCallBack;

public class IncomeViewModel extends BaseViewModel {

    private IncomeViewCallBack callBack;

    public IncomeViewModel() {
    }

    public void setCallBack(IncomeViewCallBack callBack) {
        this.callBack = callBack;
    }

    public void requestIncomeInfo() {
        IncomeModel.requestIncomeInfo(new OnLoadListener<IncomeBean>() {
            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<IncomeBean> bean) {
                if (callBack != null) {
                    callBack.requestIncomeInfoSucc(bean.getData());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                if (callBack != null) {
                    callBack.requestIncomeInfoFail(errMsg);
                }
            }
        });
    }
}
