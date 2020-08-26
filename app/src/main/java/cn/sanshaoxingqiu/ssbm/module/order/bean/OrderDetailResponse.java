package cn.sanshaoxingqiu.ssbm.module.order.bean;

import com.google.gson.annotations.SerializedName;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;

public class OrderDetailResponse {

    public String salebill_id;
    public String create_date;
    public String sale_status;//AY=顾客待付款，PAYING=顾客付款中，PAID=顾客已付款 (金额进入第三方支付机构)，FINISH=订单已完成 (全部核销完毕)，CANCEL=顾客取消订单/订单支付超时，REFUNDING=顾客已申请退款，REFUNDED=顾客退款完成
    public String pay_type;//WX=微信支付，ZFB=支付宝支付，HFWX=通过汇付天下唤起微信支付，HFZFB=通过汇付天下唤起支付宝支付
    public String optr_date;
    public String qty;
    public String sum_amt;
    @SerializedName("order_product")
    public GoodsDetailInfo orderProduct;
}
