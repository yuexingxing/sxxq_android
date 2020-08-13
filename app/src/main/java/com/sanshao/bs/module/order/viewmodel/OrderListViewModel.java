package com.sanshao.bs.module.order.viewmodel;

import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.order.bean.OrderListResponse;
import com.sanshao.bs.module.order.model.OrderModel;
import com.sanshao.bs.util.ToastUtil;

/**
 * @Author yuexingxing
 * @time 2020/6/20
 */
public class OrderListViewModel extends OrderDetailViewModel {

    private String TAG = OrderListViewModel.class.getSimpleName();

    public void getOrderList(int orderState, int page, int pageSize) {
        OrderModel.getOrderList(String.valueOf(orderState), page, pageSize, new OnLoadListener<OrderListResponse>() {
            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<OrderListResponse> t) {
                if (mCallBack == null) {
                    return;
                }
                if (page == 1) {
                    mCallBack.onRefreshData(t.getContent());
                } else {
                    mCallBack.onLoadMoreData(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
                if (mCallBack != null) {
                    mCallBack.onRefreshData(null);
                }
            }
        });
    }
}
