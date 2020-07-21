package com.sanshao.bs.module.order.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
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

    public void getOrderList(int orderState, IOrderModel callback){
        OrderModel.getOrderList("", "", new OnLoadListener<OrderListResponse>() {
            @Override
            public void onLoadStart() {
                loadData(orderState, callback);
            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<OrderListResponse> t) {

            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }

    private void loadData(int orderState, IOrderModel callback){
        List<OrderInfo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.name = orderState + "玻尿酸美容护肤不二之选，还你天使容颜，变美不容错误。";
            orderInfo.state = orderState;
            list.add(orderInfo);
        }
        if(callback != null){
            callback.onRefreshData(list);
        }
    }
}
