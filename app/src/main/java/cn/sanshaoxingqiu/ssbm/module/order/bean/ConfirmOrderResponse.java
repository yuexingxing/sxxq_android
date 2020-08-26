package cn.sanshaoxingqiu.ssbm.module.order.bean;

import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;

import java.util.List;

public class ConfirmOrderResponse {

    public StoreInfo storeInfo;
    public String nickName;
    public String phone;
    public List<GoodsDetailInfo> goodsTypeDetailInfoList;
}
