package com.sanshao.bs.module.order.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.order.bean.ConfirmOrderResponse;
import com.sanshao.bs.module.order.bean.StoreInfo;
import com.sanshao.bs.module.order.model.IConfirmOrderModel;
import com.sanshao.bs.module.order.model.OrderModel;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yuexingxing
 * @time 2020/6/20
 */
public class ConfirmOrderViewModel extends BaseViewModel {
    private String TAG = ConfirmOrderViewModel.class.getSimpleName();
    private IConfirmOrderModel mCallBack;

    public void setCallBack(IConfirmOrderModel iConfirmOrderModel) {
        mCallBack = iConfirmOrderModel;
    }

    public void getOrderInfo() {

        OrderModel.getOrderInfo(new OnLoadListener<ConfirmOrderResponse>() {

            @Override
            public void onLoadStart() {
                loadData();
            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<ConfirmOrderResponse> t) {

            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }

    public void submitOrderInfo(GoodsDetailInfo goodsDetailInfo) {

        OrderModel.submitOrderInfo(goodsDetailInfo, new OnLoadListener<ConfirmOrderResponse>() {

            @Override
            public void onLoadStart() {
                if (mCallBack != null) {
                    mCallBack.returnSubmitOrderInfo();
                }
            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse t) {

            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }

    private void loadData() {

        ConfirmOrderResponse confirmOrderResponse = new ConfirmOrderResponse();
        StoreInfo storeInfo = new StoreInfo();
        storeInfo.time = "周一至周五：10:00-22:00";
        storeInfo.tel = "021-4563125/18756531425";
        storeInfo.address = "上海市静安区新闸路829号丽都新贵上海市静安区新闸路829号丽都新贵大厦304、305、306室大厦304、305、306室";

        List<GoodsDetailInfo> goodsTypeDetailInfoList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            GoodsDetailInfo orderInfo = GoodsDetailInfo.getGoodsDetailInfo();
            orderInfo.sarti_name = "玻尿酸美容护肤不二之选，还你天使容颜，变美不容错误。";
            orderInfo.stock = 5;
            orderInfo.buyNum = 1;
            orderInfo.sarti_mkprice = 102.2;
            orderInfo.thumbnail_img = Constants.DEFAULT_IMG_URL;
            goodsTypeDetailInfoList.add(orderInfo);
        }
        confirmOrderResponse.nickName = "王小丫";
        confirmOrderResponse.phone = "13343223367";
        confirmOrderResponse.storeInfo = storeInfo;
        confirmOrderResponse.goodsTypeDetailInfoList = goodsTypeDetailInfoList;
        if (mCallBack != null) {
            mCallBack.returnConfirmOrder(confirmOrderResponse);
        }
    }
}
