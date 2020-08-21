package com.sanshao.bs.module.order.model;

import com.sanshao.bs.module.order.bean.OrderPayInfoResponse;
import com.sanshao.bs.module.order.bean.OrderStatusResponse;

public interface IPayModel {
    void returnOrderPayInfo(int optType, OrderPayInfoResponse orderPayInfoResponse);

    void returnOrderStatus(OrderStatusResponse orderStatusResponse);
}
