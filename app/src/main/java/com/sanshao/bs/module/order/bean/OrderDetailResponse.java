package com.sanshao.bs.module.order.bean;

import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;

import java.util.List;

public class OrderDetailResponse {
    public GoodsDetailInfo goodsDetailInfo;
    public List<OrderInfo> serverList;
    public List<OrderInfo> remainingServerList;
}
