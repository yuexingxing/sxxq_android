package com.sanshao.bs.module.order.model;

import com.sanshao.bs.module.order.bean.OrderInfo;

import java.util.List;

public interface IOrderModel {
    void onRefreshData(List<OrderInfo> list);
}
