package com.sanshao.bs.module.order.model;

import com.sanshao.bs.module.order.bean.ConfirmOrderResponse;
import com.sanshao.bs.module.order.bean.CreateOrderResponse;

public interface IConfirmOrderModel {
    void returnCreateOrderInfo(CreateOrderResponse createOrderResponse);

    void returnConfirmOrder(ConfirmOrderResponse confirmOrderResponse);

    void returnSubmitOrderInfo();
}
