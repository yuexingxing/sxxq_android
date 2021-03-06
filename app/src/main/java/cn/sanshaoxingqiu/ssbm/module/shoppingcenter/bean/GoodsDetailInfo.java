package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import cn.sanshaoxingqiu.ssbm.SSApplication;

import com.exam.commonbiz.bean.UserInfo;

import cn.sanshaoxingqiu.ssbm.util.MathUtil;

import java.io.Serializable;
import java.util.List;

public class GoodsDetailInfo implements Serializable, MultiItemEntity {

    public interface GOODS_TYPE {
        int REAL_DATA = 1;
        int WITH_LAST_DATA = 2;
    }

    public interface PAY_TYPE {
        String MONEY = "MONEY";
        String POINT = "POINT";
        String DEPOSIT = "DEPOSIT";//定金
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
    public GoodsDetailInfo order_product;
    public List<GoodsDetailInfo> product_list;
    public List<GoodsDetailInfo> set_meal_list;
    public List<WriteOffInfo> write_off;
    public boolean isPlay;
    public float total_amt;
    public String used;//已使用服务次数
    public String unused;//未使用服务次数
    public String pay_type;
    public int sarti_point_price;
    public double deposit_price;
    public String type;
    public String code;
    public String salebill_id;
    public String create_date;
    public String optr_date;
    public String sum_amt;
    public int sum_point;
    public int qty;
    public String sale_status;//PAY=顾客待付款，PAYING=顾客付款中，PAID=顾客已付款 (金额进入第三方支付机构)，FINISH=订单已完成 (全部核销完毕)，CANCEL=顾客取消订单/订单支付超时，REFUNDING=顾客已申请退款，REFUNDED=顾客退款完成 ,
    public int itemType = GOODS_TYPE.REAL_DATA;
    public List<MemberCommissionInfo> mem_commission_config;
    public GoodsDetailInfo shopSartiInfo;
    public String unit_price;

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
     * 是不是定金支付
     *
     * @return
     */
    public boolean isPayByDisposit() {
        if (TextUtils.equals(PAY_TYPE.DEPOSIT, pay_type)) {
            return true;
        }
        if (order_product == null) {
            return false;
        }
        return TextUtils.equals(PAY_TYPE.DEPOSIT, order_product.pay_type);
    }

    /**
     * 是不是金钱购买
     *
     * @return
     */
    public boolean isPayByMoney() {
        return !isFree() && !isPayByPoint() && !isPayByDisposit();
    }

    public String getPriceText() {
        if (isFree()) {
            return "免费领取";
        } else if (isPayByPoint()) {
            return sarti_point_price + "分享金";
        } else if (isPayByDisposit()) {
            return "¥" + MathUtil.getNumExclude0(deposit_price);
        }
        return "¥" + MathUtil.getNumExclude0(sarti_saleprice);
    }

    public String getOrderDetailPriceText() {
        if (isFree()) {
            return "¥0";
        } else if (isPayByPoint()) {
            return sarti_point_price + "分享金";
        } else if (isPayByDisposit()) {
            return "¥" + MathUtil.getNumExclude0(deposit_price);
        }
        return "¥" + MathUtil.getNumExclude0(sarti_saleprice);
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    /**
     * 是否是套餐
     *
     * @return
     */
    public boolean isMeal() {
        return is_package == 1;
    }

    public String getSharePath() {
        UserInfo userInfo = SSApplication.getInstance().getUserInfo();
        String path = "/pages/mall/goodsDetail?sarti_id=" + sarti_id + "&mem=" + userInfo.invitation_code;
        return path;
    }

    /**
     * 获取小程序的注册界面
     *
     * @param artitagId
     * @return
     */
    public String getRegistrationSharePath(String artitagId) {
        UserInfo userInfo = SSApplication.getInstance().getUserInfo();
        return String.format("/pages/activity/registration?mem=%s&artitag_id=%s", userInfo.invitation_code, artitagId);
    }

    public boolean isOneStarMember() {
        return TextUtils.equals("1", mem_class_key);
    }

    public boolean isTwoStarMember() {
        return TextUtils.equals("2", mem_class_key);
    }

    public boolean isThreeStarMember() {
        return TextUtils.equals("3", mem_class_key);
    }

    public static class MemberCommissionInfo implements Serializable {
        public String amt;
        public String mem_class_key;

        public String getMember() {
            if (TextUtils.equals("1", mem_class_key)) {
                return "一星粉丝";
            } else if (TextUtils.equals("2", mem_class_key)) {
                return "二星粉丝";
            } else if (TextUtils.equals("3", mem_class_key)) {
                return "三星粉丝";
            } else {
                return "普通用户";
            }
        }
    }

    //是不是会员
    public boolean isMember() {
        if (mem_class_key == null || TextUtils.isEmpty(mem_class_key)) {
            return false;
        }
        return true;
    }


    public String getMember() {
        if (TextUtils.isEmpty(mem_class_key)) {
            return "普通用户";
        } else if (TextUtils.equals("1", mem_class_key)) {
            return "一星粉丝";
        } else if (TextUtils.equals("2", mem_class_key)) {
            return "二星粉丝";
        } else {
            return "三星粉丝";
        }
    }

    //核销码
    public class WriteOffInfo implements Serializable {
        public String salebillId;
        public String type;
        public String code;

        public boolean canUse() {
            return TextUtils.equals("ENABLE", type);
        }
    }
}
