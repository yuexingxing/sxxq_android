package com.sanshao.bs.module.order.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.order.bean.OrderPayInfoResponse;
import com.sanshao.bs.module.order.bean.OrderStatusResponse;
import com.sanshao.bs.module.order.model.IPayModel;
import com.sanshao.bs.module.order.model.PayModel;
import com.sanshao.bs.util.ToastUtil;

/**
 * @Author yuexingxing
 * @time 2020/6/20
 */
public class PayViewModel extends BaseViewModel {
    private String TAG = PayViewModel.class.getSimpleName();
    private IPayModel mCallBack;

    public void setCallBack(IPayModel iConfirmPayModel) {
        mCallBack = iConfirmPayModel;
    }

    public void getOrderPayInfo(int payType) {

        PayModel.getOrderPayInfo(payType, new OnLoadListener<OrderPayInfoResponse>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<OrderPayInfoResponse> t) {
                if (mCallBack != null) {
                    mCallBack.returnOrderPayInfo(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
            }
        });
    }

    public void getOrderStatus(String salebillId) {

        PayModel.getOrderStatus(salebillId, new OnLoadListener<OrderStatusResponse>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<OrderStatusResponse> t) {
                if (mCallBack != null) {
                    mCallBack.returnOrderStatus(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }
}
