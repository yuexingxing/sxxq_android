package com.sanshao.bs.module.order.model;

import com.sanshao.bs.module.order.bean.OrderDetailResponse;
import com.sanshao.bs.module.order.bean.OrderNumStatusResponse;

public interface IOrderDetailModel {

    void returnOrderDetailInfo(OrderDetailResponse orderDetailResponse);

    void returnOrderNumStatus(OrderNumStatusResponse orderNumStatusResponse);
}
