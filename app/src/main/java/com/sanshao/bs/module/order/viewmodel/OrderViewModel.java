package com.sanshao.bs.module.order.viewmodel;

import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.order.bean.OrderBenefitResponse;
import com.sanshao.bs.module.order.model.IOrderModel;
import com.sanshao.bs.module.order.model.OrderModel;
import com.sanshao.bs.util.ToastUtil;

public class OrderViewModel {

    public IOrderModel mCallBack;

    public void setCallBack(IOrderModel iOrderModel) {
        mCallBack = iOrderModel;
    }

    public void getOrderBenefit() {

        OrderModel.getOrderBenefit(new OnLoadListener<OrderBenefitResponse>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<OrderBenefitResponse> t) {
                if (mCallBack != null) {
                    mCallBack.returnOrderBenefit(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
            }
        });
    }
}
