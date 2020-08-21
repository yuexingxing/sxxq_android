package com.sanshao.bs.module.order.model;

import com.sanshao.bs.module.order.bean.ConfirmOrderResponse;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;

public interface IConfirmOrderModel {
    void returnCreateOrderInfo(GoodsDetailInfo goodsDetailInfo);

    void returnConfirmOrder(ConfirmOrderResponse confirmOrderResponse);

    void returnSubmitOrderInfo();
}
