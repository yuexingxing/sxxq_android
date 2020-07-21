package com.sanshao.bs.module.order.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.order.bean.OrderDetailResponse;
import com.sanshao.bs.module.order.bean.OrderInfo;
import com.sanshao.bs.module.order.model.IOrderDetailModel;
import com.sanshao.bs.module.order.model.OrderModel;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class OrderDetailViewModel extends BaseViewModel {
    private String TAG = OrderDetailViewModel.class.getSimpleName();

    public void getOrderDetailInfo(int payType, IOrderDetailModel callback){

        OrderModel.getOrderDetailInfo(payType, new OnLoadListener<OrderDetailResponse>() {

            @Override
            public void onLoadStart() {
              initData(callback);
            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<OrderDetailResponse> t) {

            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }

    private void initData(IOrderDetailModel callback){

        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();

        List<OrderInfo> remainingServerInfoList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.name = i + "黄金微针";
            remainingServerInfoList.add(orderInfo);
        }
        orderDetailResponse.remainingServerList = remainingServerInfoList;

        List<OrderInfo> serverInfoList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.name = i + "黄金微针";
            serverInfoList.add(orderInfo);
        }
        orderDetailResponse.serverList = serverInfoList;
        GoodsDetailInfo goodsDetailInfo = GoodsDetailInfo.getGoodsDetailInfo();
        goodsDetailInfo.setMealList = null;
        orderDetailResponse.goodsDetailInfo = goodsDetailInfo;

        if (callback != null){
            callback.returnOrderDetailInfo(orderDetailResponse);
        }
    }
}
