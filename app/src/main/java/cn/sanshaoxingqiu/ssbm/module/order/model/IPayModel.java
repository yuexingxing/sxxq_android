package cn.sanshaoxingqiu.ssbm.module.order.model;

import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderPayInfoResponse;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderStatusResponse;

public interface IPayModel {
    void returnOrderPayInfo(int optType, OrderPayInfoResponse orderPayInfoResponse);

    void returnOrderStatus(OrderStatusResponse orderStatusResponse);

    void returnFVipPay(int optType, OrderPayInfoResponse orderPayInfoResponse);
}
