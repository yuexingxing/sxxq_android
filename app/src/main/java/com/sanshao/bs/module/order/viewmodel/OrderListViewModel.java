package com.sanshao.bs.module.order.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.exam.commonbiz.net.SimpleLoadCallBack;
import com.sanshao.bs.module.order.bean.OrderInfo;
import com.sanshao.bs.module.order.bean.OrderListResponse;
import com.sanshao.bs.module.order.model.IOrderModel;
import com.sanshao.bs.module.order.model.OrderModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yuexingxing
 * @time 2020/6/20
 */
public class OrderListViewModel extends BaseViewModel {

    private String TAG = OrderListViewModel.class.getSimpleName();

    private IOrderModel mCallBack;

    public void setCallBack(IOrderModel iOrderModel) {
        mCallBack = iOrderModel;
    }

    public void getOrderList(int orderState, int page, int pageSize) {
        OrderModel.getOrderList(String.valueOf(orderState), page, pageSize, new SimpleLoadCallBack<OrderInfo>(mCallBack) {

            @Override
            public void onLoadSucessed(BaseResponse<OrderInfo> t) {
                mCallBack.onRefreshData(t != null ? t.getContent() : null);
            }

            @Override
            public void onLoadFailed(String errMsg) {
                mCallBack.onRefreshData(null);
            }
        });
    }
}
