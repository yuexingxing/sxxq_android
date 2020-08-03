package com.sanshao.bs.module.shoppingcenter.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.sanshao.bs.util.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GoodsDetailInfo implements Serializable {
    public String sarti_id;//商品子ID
    @JSONField(name="sarti_name")
    public String sartiName;
    @JSONField(name="sarti_sale_price")
    public double sartiSalePrice;//售价
    @JSONField(name="sarti_mkprice")
    public double sartiMkPrice;//市场价
    public int buyNum;
    public int stock;//库存
    @JSONField(name="sarti_img")
    public String sartiImg;//商品介绍图/视频
    public boolean checked;
    public int position;
    public List<GoodsDetailInfo> setMealList;//套餐
    public String videoPlayUrl;
    public String instruction;
    public String thumbnail_img;//封面缩略图
    public String sarti_marketing_text;//商品营销文字
    public int is_package;//0=非套餐，1=套餐
    public String mem_class_key;//商品星级
    public String share_code;//商品分享码
    public String share_url;//商品分享码图片
    public String sellNum;
    public String use_qty;

    public static GoodsDetailInfo getGoodsDetailInfo(){

        GoodsDetailInfo goodsDetailInfo = new GoodsDetailInfo();
        goodsDetailInfo.sartiName = "玻尿酸美容护肤不二之选，还你天使容颜，变美不容错误。";
        goodsDetailInfo.thumbnail_img = Constants.DEFAULT_IMG_URL;
        goodsDetailInfo.sartiSalePrice = 134;
        goodsDetailInfo.sartiMkPrice = 99;

        List<GoodsDetailInfo> setMealList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            GoodsDetailInfo goodsDetailInfo2 = new GoodsDetailInfo();
            goodsDetailInfo2.sartiName = "玻尿酸美容护肤不二之选，还你天使容颜，变美不容错误。";
            goodsDetailInfo2.thumbnail_img = Constants.DEFAULT_IMG_URL;
            goodsDetailInfo2.sartiSalePrice = 1234;
            goodsDetailInfo2.sartiMkPrice = 999;
            setMealList.add(goodsDetailInfo2);
        }
        goodsDetailInfo.setMealList = setMealList;

        return goodsDetailInfo;
    }
}
