package com.sanshao.bs.module.order.bean;

import com.google.gson.annotations.SerializedName;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;

import java.util.List;

public class OrderDetailResponse {

    public String salebill_id;
    public String create_date;
    public String sale_status;//AY=顾客待付款，PAYING=顾客付款中，PAID=顾客已付款 (金额进入第三方支付机构)，FINISH=订单已完成 (全部核销完毕)，CANCEL=顾客取消订单/订单支付超时，REFUNDING=顾客已申请退款，REFUNDED=顾客退款完成
    public String pay_type;//WX=微信支付，ZFB=支付宝支付，HFWX=通过汇付天下唤起微信支付，HFZFB=通过汇付天下唤起支付宝支付
    public String optr_date;
    public String qty;
    public String sum_amt;
    @SerializedName("order_product")
    public OrderProductInfo orderProduct;

    public class OrderProductInfo extends GoodsDetailInfo {

        public List<String> used_write_off;//已使用核销码
        public List<String> unused_write_off;//未使用核销码
        public List<String> writeoff_time;//核销时间
    }
}
