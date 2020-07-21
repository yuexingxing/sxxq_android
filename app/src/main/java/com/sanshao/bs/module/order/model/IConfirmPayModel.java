package com.sanshao.bs.module.order.model;

import com.sanshao.bs.module.order.bean.OrderPayInfoResponse;

public interface IConfirmPayModel {
    void returnOrderPayInfo(OrderPayInfoResponse orderPayInfoResponse);
}
