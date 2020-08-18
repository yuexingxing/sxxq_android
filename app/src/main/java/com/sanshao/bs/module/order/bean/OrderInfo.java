package com.sanshao.bs.module.order.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @Author yuexingxing
 * @time 2020/7/1
 */
public class OrderInfo implements Serializable {

    public static final String ORDER_STATE = "order_state";

    // 订单ID
    @SerializedName("salebill_id")
    public String id;

    // 商品/套餐名称
    @SerializedName("sarti_name")
    public String name;

    // 商品缩略图
    @SerializedName("thumbnail_img")
    public String thumbnailImg;

    // 订单实付总价
    @SerializedName("sum_amt")
    public String totalPrice;

    //总积分
    @SerializedName("sum_point")
    public String sumPoint;

    // 商品件数
    @SerializedName("qty")
    public int count;

    // 订单状态
    @SerializedName("salebill_status")
    public String status;

    // 预约状态
    @SerializedName("status")
    public String appointmentStatus;

    @SerializedName("sale_status")
    public String saleStatus;

    // 地址
    @SerializedName("address")
    public String address;

    // 订单实际支付价格
    public String actualPrice;

    // 订单时间
    public String time;

    // 是否是套餐
    public boolean isSalesPackage;

    // 套餐内商品列表
    public Product shopSartiInfo;

    public static class Product implements Serializable {

        // 商品名称
        @SerializedName("sarti_name")
        public String name;

        // 缩略图
        @SerializedName("thumbnail_img")
        public String thumbnailImg;

        // 商品售价
        @SerializedName("sart_saleprice")
        public String salePrice;

        // 商品市场价
        @SerializedName("sarti_mkprice")
        public String mkPrice;

        // 商品件数
        @SerializedName("use_qty")
        public String count;
    }

    public static String getOrderStatus(String status) {
        if (TextUtils.isEmpty(status)) {
            return "";
        } else if (TextUtils.equals(status, ORDER_STATUS.PAY)) {
            return "待支付";
        } else if (TextUtils.equals(status, ORDER_STATUS.PAID)) {
            return "待使用";
        } else if (TextUtils.equals(status, ORDER_STATUS.FINISH)) {
            return "已完成";
        } else if (TextUtils.equals(status, ORDER_STATUS.PAYING)) {
            return "付款中";
        } else if (TextUtils.equals(status, ORDER_STATUS.CANCEL)) {
            return "取消支付";
        } else if (TextUtils.equals(status, ORDER_STATUS.REFUNDING)) {
            return "申请退款";
        } else {
            return "退款完成";
        }
    }

    public interface ORDER_STATUS {
        String PAY = "PAY";
        String PAID = "PAID";
        String FINISH = "FINISH";
        String PAYING = "PAYING";
        String CANCEL = "CANCEL";
        String REFUNDING = "REFUNDING";
        String REFUNDED = "REFUNDED";
    }

    public interface State {
        int ALL = 0;
        int ToBePaid = 1;
        int ToBeUse = 2;
        int Complete = 3;
    }
}
