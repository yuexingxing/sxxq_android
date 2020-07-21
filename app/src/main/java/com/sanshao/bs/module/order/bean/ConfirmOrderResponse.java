package com.sanshao.bs.module.order.bean;

import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;

import java.util.List;

public class ConfirmOrderResponse {

    public StoreInfo storeInfo;
    public String nickName;
    public String phone;
    public List<GoodsDetailInfo> goodsTypeDetailInfoList;
}
