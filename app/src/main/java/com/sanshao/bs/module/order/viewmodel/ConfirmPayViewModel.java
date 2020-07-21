package com.sanshao.bs.module.order.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.order.bean.OrderPayInfoResponse;
import com.sanshao.bs.module.order.model.IConfirmPayModel;
import com.sanshao.bs.module.order.model.OrderModel;

/**
 * @Author yuexingxing
 * @time 2020/6/20
 */
public class ConfirmPayViewModel extends BaseViewModel {
    private String TAG = ConfirmPayViewModel.class.getSimpleName();

    public void getOrderPayInfo(int payType, IConfirmPayModel callback){

        OrderModel.getOrderPayInfo(payType, new OnLoadListener<OrderPayInfoResponse>() {

            @Override
            public void onLoadStart() {
                if (callback != null){
                    callback.returnOrderPayInfo(null);
                }
            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<OrderPayInfoResponse> t) {

            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }
}
