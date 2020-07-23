package com.sanshao.bs.module.shoppingcenter.bean;

import com.sanshao.bs.util.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GoodsDetailInfo implements Serializable {
    public String name;
    public String id;
    public double oldPrice;
    public double price;
    public int buyNum;
    public int stock;//库存
    public String icon;
    public boolean checked;
    public int position;
    public List<GoodsDetailInfo> setMealList;//套餐
    public String videoPlayUrl;
    public String instruction;

    public static GoodsDetailInfo getGoodsDetailInfo(){

        GoodsDetailInfo goodsDetailInfo = new GoodsDetailInfo();
        goodsDetailInfo.name = "玻尿酸美容护肤不二之选，还你天使容颜，变美不容错误。";
        goodsDetailInfo.icon = Constants.DEFAULT_IMG_URL;
        goodsDetailInfo.oldPrice = 134;
        goodsDetailInfo.price = 99;

        List<GoodsDetailInfo> setMealList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            GoodsDetailInfo goodsDetailInfo2 = new GoodsDetailInfo();
            goodsDetailInfo2.name = "玻尿酸美容护肤不二之选，还你天使容颜，变美不容错误。";
            goodsDetailInfo2.icon = Constants.DEFAULT_IMG_URL;
            goodsDetailInfo2.oldPrice = 1234;
            goodsDetailInfo2.price = 999;
            setMealList.add(goodsDetailInfo2);
        }
        goodsDetailInfo.setMealList = setMealList;

        return goodsDetailInfo;
    }
}
