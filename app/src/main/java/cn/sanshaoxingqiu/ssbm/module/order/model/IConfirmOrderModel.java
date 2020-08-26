package cn.sanshaoxingqiu.ssbm.module.order.model;

import cn.sanshaoxingqiu.ssbm.module.order.bean.ConfirmOrderResponse;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;

public interface IConfirmOrderModel {
    void returnCreateOrderInfo(GoodsDetailInfo goodsDetailInfo);

    void returnConfirmOrder(ConfirmOrderResponse confirmOrderResponse);

    void returnSubmitOrderInfo();
}
