package com.sanshao.bs.module.order.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.order.bean.OrderNumStatusResponse;
import com.sanshao.bs.module.order.model.IOrderDetailModel;
import com.sanshao.bs.module.order.model.OrderModel;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.util.ToastUtil;

/**
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class OrderDetailViewModel extends BaseViewModel {
    private String TAG = OrderDetailViewModel.class.getSimpleName();

    public IOrderDetailModel mCallBack;

    public void setCallBack(IOrderDetailModel iOrderDetailModel) {
        mCallBack = iOrderDetailModel;
    }

    public void getOrderDetailInfo(String salebillId) {

        OrderModel.getOrderDetailInfo(salebillId, new OnLoadListener<GoodsDetailInfo>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<GoodsDetailInfo> t) {
                if (mCallBack != null) {
                    mCallBack.returnOrderDetailInfo(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
            }
        });
    }

    public void getOrderNumStatus() {

        OrderModel.getOrderNumStatus(new OnLoadListener<OrderNumStatusResponse>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<OrderNumStatusResponse> t) {
                if (mCallBack != null) {
                    mCallBack.returnOrderNumStatus(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }


    public void cancelOrder(String salebillId) {

        OrderModel.cancelOrder(salebillId, new OnLoadListener() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse t) {
                if (mCallBack != null) {
                    mCallBack.returnCancelOrder();
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
            }
        });
    }
}
