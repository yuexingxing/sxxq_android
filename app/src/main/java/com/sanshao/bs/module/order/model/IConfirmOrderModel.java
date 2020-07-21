package com.sanshao.bs.module.order.model;

import com.sanshao.bs.module.order.bean.ConfirmOrderResponse;

public interface IConfirmOrderModel {
    void returnConfirmOrder(ConfirmOrderResponse confirmOrderResponse);
    void returnSubmitOrderInfo();
}
