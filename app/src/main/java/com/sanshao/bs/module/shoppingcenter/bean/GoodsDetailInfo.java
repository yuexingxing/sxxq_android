package com.sanshao.bs.module.shoppingcenter.bean;

import android.text.TextUtils;

import com.sanshao.bs.util.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GoodsDetailInfo implements Serializable {

    public interface PAY_TYPE {
        String MONEY = "MONEY";
        String POINT = "POINT";
    }

    public String sarti_id;//商品子ID
    public String sarti_name;
    public double sarti_saleprice;//售价
    public double sarti_mkprice;//市场价
    public int buyNum = 1;
    public int stock;//库存
    public List<VideoInfo> sarti_img;//商品介绍图/视频
    public boolean checked;
    public int position;
    public String sarti_intro;
    public String thumbnail_img;//封面缩略图
    public String sarti_marketing_text;//商品营销文字
    public int is_package;//0=非套餐，1=套餐
    public String mem_class_key;//商品星级
    public String share_code;//商品分享码
    public String share_url;//商品分享码图片
    public String sell_num;
    public String use_qty;
    public String sarti_desc;
    public List<GoodsDetailInfo> product_list;
    public boolean isPlay;
    public String used;//已使用服务次数
    public String unused;//未使用服务次数
    public String pay_type;
    public int sarti_point_price;

    public String getPointTip() {
        return sarti_point_price + "分享金";
    }

    /**
     * 是不是积分支付
     *
     * @return
     */
    public boolean isPayByPoint() {
        return TextUtils.equals(PAY_TYPE.POINT, pay_type);
    }

    public boolean isFree() {
        return !isPayByPoint() && sarti_saleprice == 0;
    }

    /**
     * 是否是套餐
     *
     * @return
     */
    public boolean isMeal() {
        return is_package == 1;
    }

    public static GoodsDetailInfo getGoodsDetailInfo() {

        GoodsDetailInfo goodsDetailInfo = new GoodsDetailInfo();
        goodsDetailInfo.sarti_name = "玻尿酸美容护肤不二之选，还你天使容颜，变美不容错误。";
        goodsDetailInfo.thumbnail_img = Constants.DEFAULT_IMG_URL;
        goodsDetailInfo.sarti_saleprice = 134;
        goodsDetailInfo.sarti_mkprice = 99;

        List<GoodsDetailInfo> setMealList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            GoodsDetailInfo goodsDetailInfo2 = new GoodsDetailInfo();
            goodsDetailInfo2.sarti_name = "玻尿酸美容护肤不二之选，还你天使容颜，变美不容错误。";
            goodsDetailInfo2.thumbnail_img = Constants.DEFAULT_IMG_URL;
            goodsDetailInfo2.sarti_saleprice = 1234;
            goodsDetailInfo2.sarti_mkprice = 999;
            setMealList.add(goodsDetailInfo2);
        }
        goodsDetailInfo.product_list = setMealList;

        return goodsDetailInfo;
    }
}
