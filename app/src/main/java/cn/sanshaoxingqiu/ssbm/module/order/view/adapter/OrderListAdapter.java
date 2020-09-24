package cn.sanshaoxingqiu.ssbm.module.order.view.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;

import com.exam.commonbiz.util.ContainerUtil;
import com.exam.commonbiz.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import cn.sanshaoxingqiu.ssbm.util.MathUtil;

/**
 * @Author yuexingxing
 * @time 2020/7/2
 */
public class OrderListAdapter extends BaseQuickAdapter<GoodsDetailInfo, BaseViewHolder> {

    private OnItemClickListener mCallBack;

    public OrderListAdapter() {
        super(R.layout.item_layout_order_list, null);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mCallBack = onItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailInfo item) {
        GoodsDetailInfo product = item.shopSartiInfo;
        if (product == null) {
            return;
        }
        helper.setText(R.id.tv_title, product.sarti_name);
        helper.setText(R.id.tv_total_price1, product.getPriceText());
        helper.setText(R.id.tv_count, "x" + item.qty);

        helper.getView(R.id.ll_tobe_paid).setVisibility(View.GONE);
        helper.getView(R.id.ll_tobe_use).setVisibility(View.GONE);
        helper.getView(R.id.ll_complete).setVisibility(View.GONE);
        helper.getView(R.id.ll_canceled).setVisibility(View.GONE);
        helper.getView(R.id.ll_deposit).setVisibility(View.GONE);

        helper.setText(R.id.tv_content_tip, String.format("共计%s件商品；实收：%s元", item.qty, MathUtil.getNumExclude0(item.total_amt)));
        helper.setText(R.id.tv_state, item.getOrderStatus(item.sale_status));
        if (TextUtils.equals(OrderInfo.ORDER_STATUS.PAY, item.sale_status) ||
                TextUtils.equals(OrderInfo.ORDER_STATUS.PAYING, item.sale_status)) {
            helper.getView(R.id.ll_tobe_paid).setVisibility(View.VISIBLE);
        } else if (TextUtils.equals(OrderInfo.ORDER_STATUS.PAID, item.sale_status)) {
            helper.getView(R.id.ll_tobe_use).setVisibility(View.VISIBLE);
        } else if (TextUtils.equals(OrderInfo.ORDER_STATUS.CANCEL, item.sale_status)) {
            helper.getView(R.id.ll_canceled).setVisibility(View.VISIBLE);
        } else if (TextUtils.equals(OrderInfo.ORDER_STATUS.PAY_GAP, item.sale_status)) {
            if (item.shopSartiInfo != null) {
                String lastFee = MathUtil.getNumExclude0(item.qty * (item.shopSartiInfo.sarti_saleprice - item.shopSartiInfo.deposit_price));
                String fundFee = MathUtil.getNumExclude0(item.qty * item.shopSartiInfo.deposit_price);
                helper.getView(R.id.ll_deposit).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_content_tip, String.format("尾款: %s元 共计%s件商品；定金实付：%s元",
                        lastFee, item.qty, fundFee));
            }
        }

        if (item.shopSartiInfo != null) {
            if (TextUtils.equals(item.shopSartiInfo.pay_type, GoodsDetailInfo.PAY_TYPE.DEPOSIT)) {
                helper.setText(R.id.tv_total_price1, "¥" + MathUtil.getNumExclude0(item.shopSartiInfo.deposit_price));
            }
        }

        GlideUtil.loadImage(item.shopSartiInfo.thumbnail_img, helper.getView(R.id.iv_icon));

        FrameLayout flSetMeal = helper.getView(R.id.fl_set_meal);
        LinearLayout llOpenSetMeal = helper.getView(R.id.ll_more_setmeal);
        LinearLayout llCloseSetMeal = helper.getView(R.id.ll_close_setmeal);

        llOpenSetMeal.setOnClickListener(v -> {
            llOpenSetMeal.setVisibility(View.GONE);
            flSetMeal.setVisibility(View.VISIBLE);
        });
        llCloseSetMeal.setOnClickListener(v -> {
            flSetMeal.setVisibility(View.GONE);
            llOpenSetMeal.setVisibility(View.VISIBLE);
        });

        if (!ContainerUtil.isEmpty(product.set_meal_list)) {
            if (flSetMeal.getVisibility() == View.GONE) {
                llOpenSetMeal.setVisibility(View.VISIBLE);
            }
            RecyclerView recyclerView = helper.getView(R.id.recycler_view);
            SetMealAdapter setMealAdapter = new SetMealAdapter();
            setMealAdapter.setOptType(ConfirmOrderAdapter.OPT_TYPE_ORDER_LIST);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(helper.itemView.getContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(setMealAdapter);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setFocusable(false);
            setMealAdapter.setNewData(product.set_meal_list);
        } else {
            llOpenSetMeal.setVisibility(View.GONE);
        }

        List<GoodsDetailInfo> productList = new ArrayList<>();
        if (item.isMeal() || (item.order_product != null && item.order_product.isMeal())) {
            if (item.order_product != null && !ContainerUtil.isEmpty(item.order_product.product_list)) {
                productList.addAll(item.order_product.product_list);
            }
        } else {
            productList.add(item.order_product);
        }

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
        void onOrderDetail(GoodsDetailInfo item);

        void onSubscribe(GoodsDetailInfo item);

        void onViewCouponCode(GoodsDetailInfo item);

        void onDeleteOrder(int position, GoodsDetailInfo item);

        void onCancelOrder(int position, GoodsDetailInfo item);

        void onPay(GoodsDetailInfo item);
    }
}
