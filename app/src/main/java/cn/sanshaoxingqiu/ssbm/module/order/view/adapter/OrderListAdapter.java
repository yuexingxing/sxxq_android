package cn.sanshaoxingqiu.ssbm.module.order.view.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;

import com.exam.commonbiz.util.GlideUtil;

import cn.sanshaoxingqiu.ssbm.util.MathUtil;

/**
 * @Author yuexingxing
 * @time 2020/7/2
 */
public class OrderListAdapter extends BaseQuickAdapter<OrderInfo, BaseViewHolder> {

    private OnItemClickListener mCallBack;

    public OrderListAdapter() {
        super(R.layout.item_layout_order_list, null);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mCallBack = onItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderInfo item) {
        GoodsDetailInfo product = item.shopSartiInfo;
        if (product == null) {
            return;
        }
        helper.setText(R.id.tv_title, product.sarti_name);
        helper.setText(R.id.tv_total_price1, product.getPriceText());
        helper.setText(R.id.tv_count, "x" + item.count);

        helper.getView(R.id.ll_tobe_paid).setVisibility(View.GONE);
        helper.getView(R.id.ll_tobe_use).setVisibility(View.GONE);
        helper.getView(R.id.ll_complete).setVisibility(View.GONE);
        helper.getView(R.id.ll_canceled).setVisibility(View.GONE);
        helper.getView(R.id.ll_deposit).setVisibility(View.GONE);

        helper.setText(R.id.tv_content_tip, String.format("共计%s件商品；实收：%s元", item.count, MathUtil.getNumExclude0(item.totalPrice)));
        helper.setText(R.id.tv_state, item.getOrderStatus(item.saleStatus));
        if (TextUtils.equals(OrderInfo.ORDER_STATUS.PAY, item.saleStatus)) {
            helper.getView(R.id.ll_tobe_paid).setVisibility(View.VISIBLE);
        } else if (TextUtils.equals(OrderInfo.ORDER_STATUS.PAID, item.saleStatus)) {
            helper.getView(R.id.ll_tobe_use).setVisibility(View.VISIBLE);
        } else if (TextUtils.equals(OrderInfo.ORDER_STATUS.CANCEL, item.saleStatus)) {
            helper.getView(R.id.ll_canceled).setVisibility(View.VISIBLE);
        } else if (TextUtils.equals(OrderInfo.ORDER_STATUS.PAY_GAP, item.saleStatus)) {
            if (item.shopSartiInfo != null) {
                String lastFee = MathUtil.getNumExclude0(item.count * (item.shopSartiInfo.sarti_saleprice - item.shopSartiInfo.deposit_price));
                String fundFee = MathUtil.getNumExclude0(item.count * item.shopSartiInfo.deposit_price);
                helper.getView(R.id.ll_deposit).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_content_tip, String.format("尾款: %s元 共计%s件商品；定金实付：%s元",
                        lastFee, item.count, fundFee));
            }
        }

        GlideUtil.loadImage(item.shopSartiInfo.thumbnail_img, helper.getView(R.id.iv_icon));

        helper.getView(R.id.rl_bg).setOnClickListener(v -> {
            if (mCallBack != null) {
                mCallBack.onOrderDetail(item);
            }
        });
        helper.getView(R.id.rl_pay).setOnClickListener(v -> {
            if (mCallBack != null) {
                mCallBack.onPay(item);
            }
        });
        helper.getView(R.id.rl_cancel_order).setOnClickListener(v -> {
            if (mCallBack != null) {
                mCallBack.onCancelOrder(helper.getAdapterPosition(), item);
            }
        });
        helper.getView(R.id.tv_subscribe).setOnClickListener(v -> {
            if (mCallBack != null) {
                mCallBack.onSubscribe(item);
            }
        });
        helper.getView(R.id.ll_viewcode_complete).setOnClickListener(v -> {
            if (mCallBack != null) {
                mCallBack.onViewCouponCode(item);
            }
        });
        helper.getView(R.id.ll_viewcode_tobeuse).setOnClickListener(v -> {
            if (mCallBack != null) {
                mCallBack.onViewCouponCode(item);
            }
        });
        helper.getView(R.id.tv_delete).setOnClickListener(v -> {
            if (mCallBack != null) {
                mCallBack.onDeleteOrder(helper.getAdapterPosition(), item);
            }
        });
        helper.getView(R.id.rl_canceled_delete).setOnClickListener(v -> {
            if (mCallBack != null) {
                mCallBack.onDeleteOrder(helper.getAdapterPosition(), item);
            }
        });
        helper.getView(R.id.tv_deposit).setOnClickListener(v -> {

        });

        if (helper.getAdapterPosition() == getData().size() - 1) {
            helper.getView(R.id.view_bottom).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.view_bottom).setVisibility(View.GONE);
        }
    }

    public interface OnItemClickListener {
        void onOrderDetail(OrderInfo item);

        void onSubscribe(OrderInfo item);

        void onViewCouponCode(OrderInfo item);

        void onDeleteOrder(int position, OrderInfo item);

        void onCancelOrder(int position, OrderInfo item);

        void onPay(OrderInfo item);
    }
}
