package com.sanshao.bs.module.order.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.order.bean.OrderDetailResponse;
import com.sanshao.bs.module.order.bean.OrderInfo;
import com.sanshao.bs.module.order.bean.OrderNumStatusResponse;
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

    private IOrderDetailModel mCallBack;

    public void setCallBack(IOrderDetailModel iOrderDetailModel) {
        mCallBack = iOrderDetailModel;
    }

    public void getOrderDetailInfo(int payType) {

        OrderModel.getOrderDetailInfo(payType, new OnLoadListener<OrderDetailResponse>() {

            @Override
            public void onLoadStart() {
                initData();
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

    public void getOrderNumStatus() {

        OrderModel.getOrderNumStatus(new OnLoadListener<OrderNumStatusResponse>() {

            @Override
            public void onLoadStart() {
                initData();
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

    private void initData() {

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
        goodsDetailInfo.product_list = null;
        orderDetailResponse.goodsDetailInfo = goodsDetailInfo;

        if (mCallBack != null) {
            mCallBack.returnOrderDetailInfo(orderDetailResponse);
        }
    }
}
