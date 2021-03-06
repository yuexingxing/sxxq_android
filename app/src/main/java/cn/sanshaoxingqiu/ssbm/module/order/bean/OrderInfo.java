package cn.sanshaoxingqiu.ssbm.module.order.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;

import java.io.Serializable;

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
    public int sumPoint;

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

    public String is_package;

    public boolean isMeal() {
        return TextUtils.equals("1", is_package);
    }

    // 是否是套餐
    public boolean isSalesPackage;

    // 套餐内商品列表
    public GoodsDetailInfo shopSartiInfo;

    public static String getOrderStatus(String status) {
        if (TextUtils.isEmpty(status)) {
            return "";
        } else if (TextUtils.equals(status, ORDER_STATUS.PAY)) {
            return "待支付";
        } else if (TextUtils.equals(status, ORDER_STATUS.PAY_GAP)) {
            return "待支付尾款";
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
        String PAY_GAP = "PAY_GAP";
    }

    public interface State {
        int ALL = 0;
        int ToBePaid = 1;
        int ToBeUse = 2;
        int Complete = 3;
    }
}
