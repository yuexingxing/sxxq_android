package com.sanshao.bs.module.order.model;

import com.exam.commonbiz.base.IBaseModel;
import com.sanshao.bs.module.order.bean.OrderNumStatusResponse;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;

public interface IOrderDetailModel extends IBaseModel {

    void returnOrderDetailInfo(GoodsDetailInfo goodsDetailInfo);

    void returnOrderNumStatus(OrderNumStatusResponse orderNumStatusResponse);

    void returnCancelOrder();
}
