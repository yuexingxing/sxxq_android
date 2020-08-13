package com.sanshao.bs.module.order.view.adapter;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.util.ContainerUtil;
import com.exam.commonbiz.util.ScreenUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.module.order.view.ViewCouponCodeFragment;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.util.GlideUtil;
import com.sanshao.bs.util.MathUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yuexingxing
 * @time 2020/7/8
 */
public class ConfirmOrderAdapter extends BaseQuickAdapter<GoodsDetailInfo, BaseViewHolder> {
    public static final int OPT_TYPE_CONFIRM_ORDER = 1;
    public static final int OPT_TYPE_VIEW_CODE = 2;
    public static final int OPT_TYPE_ORDER_DETAIL = 3;
    public static final int OPT_TYPE_APPOINTMENT = 4;//预约问诊

    private int mOptType;
    private OnItemClickListener mCallBack;
    private FragmentManager mFragmentManager;

    public ConfirmOrderAdapter() {
        super(R.layout.item_layout_confirm_order, null);
    }

    public void setOptType(int optType) {
        mOptType = optType;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailInfo item) {

        initView(helper, item);
        helper.getView(R.id.rl_minus).setOnClickListener(v -> {
            if (mCallBack != null) {
                mCallBack.onMinusClick(helper.getAdapterPosition(), item);
            }
        });
        helper.getView(R.id.rl_plus).setOnClickListener(v -> {
            if (mCallBack != null) {
                mCallBack.onPlusClick(helper.getAdapterPosition(), item);
            }
        });

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

        helper.getView(R.id.include_goods_single).setVisibility(View.GONE);
        helper.getView(R.id.ll_mulity_set_meal).setVisibility(View.GONE);
        helper.getView(R.id.tv_goods_count).setVisibility(View.GONE);

        LinearLayout llConentTop = helper.getView(R.id.ll_content_top);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llConentTop.getLayoutParams();
        //确认订单
        if (mOptType == OPT_TYPE_CONFIRM_ORDER) {
            helper.getView(R.id.ll_right_price).setVisibility(View.GONE);
            helper.getView(R.id.ll_count_view).setVisibility(View.VISIBLE);
        } else if (mOptType == OPT_TYPE_ORDER_DETAIL) {
            helper.getView(R.id.ll_right_price).setVisibility(View.VISIBLE);
            helper.getView(R.id.ll_mulity_set_meal).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_goods_count).setVisibility(View.VISIBLE);
            helper.getView(R.id.ll_count_view).setVisibility(View.GONE);

            initOrderDetailView(helper, item);

            layoutParams.leftMargin = ScreenUtil.dp2px(SSApplication.app, 12);
            layoutParams.rightMargin = ScreenUtil.dp2px(SSApplication.app, 12);
            llConentTop.setLayoutParams(layoutParams);
        } else if (mOptType == OPT_TYPE_APPOINTMENT) {
            helper.getView(R.id.ll_right_price).setVisibility(View.GONE);
            helper.getView(R.id.ll_count_view).setVisibility(View.GONE);
        }

        //如果有套餐
        if (item.isMeal() && !ContainerUtil.isEmpty(item.product_list)) {
            if (flSetMeal.getVisibility() == View.GONE) {
                llOpenSetMeal.setVisibility(View.VISIBLE);
            }
            RecyclerView recyclerView = helper.getView(R.id.recycler_view);
            SetMealAdapter setMealAdapter = new SetMealAdapter();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(flSetMeal.getContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(setMealAdapter);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setFocusable(false);
            setMealAdapter.setNewData(item.product_list);
        } else {
            llOpenSetMeal.setVisibility(View.GONE);
        }

        if (mFragmentManager != null && item.order_product != null && !ContainerUtil.isEmpty(item.order_product.write_off)) {
            List<Fragment> mFragmentList = new ArrayList<>();
            List<GoodsDetailInfo> orderProductInfoList = new ArrayList<>();
            orderProductInfoList.add(item.order_product);
            for (int i = 0; i < orderProductInfoList.size(); i++) {
                //如果有核销码
                if (!ContainerUtil.isEmpty(orderProductInfoList.get(i).write_off)) {
                    mFragmentList.add(ViewCouponCodeFragment.newInstance(orderProductInfoList.get(i)));
                }
            }
            ViewPager viewPager = helper.getView(R.id.view_pager);
            viewPager.setVisibility(View.VISIBLE);
            TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(mFragmentManager, mFragmentList);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(0);
            viewPager.setOffscreenPageLimit(mFragmentList.size());
        } else {
            helper.getView(R.id.view_pager).setVisibility(View.GONE);
        }
    }

    private void initView(BaseViewHolder helper, GoodsDetailInfo item) {

        helper.setText(R.id.tv_title, item.sarti_name);
        helper.setText(R.id.tv_buy_count, item.buyNum + "");
        helper.setText(R.id.tv_price_1, "¥" + MathUtil.getNumExclude0(item.sum_amt));
        helper.setText(R.id.tv_price_2, "¥" + MathUtil.getNumExclude0(item.sarti_saleprice));
        helper.setText(R.id.tv_total_count, "x" + item.qty);
        helper.setText(R.id.tv_goods_count, String.format("共计%s件商品；实收：%s元", item.qty, item.sum_amt));
        GlideUtil.loadImage(item.thumbnail_img, helper.getView(R.id.iv_icon));
    }

    private void initOrderDetailView(BaseViewHolder helper, GoodsDetailInfo item) {
        if (item == null || item.order_product == null) {
            return;
        }
        helper.setText(R.id.tv_title, item.order_product.sarti_name);
        helper.setText(R.id.tv_buy_count, item.buyNum + "");
        helper.setText(R.id.tv_price_1, "¥" + MathUtil.getNumExclude0(item.sum_amt));
        helper.setText(R.id.tv_price_2, "上海市");
        helper.setText(R.id.tv_total_count, "x" + item.qty);
        helper.setText(R.id.tv_goods_count, String.format("共计%s件商品；实收：%s元", item.qty, MathUtil.getNumExclude0(item.sum_amt)));
        GlideUtil.loadImage(item.order_product.thumbnail_img, helper.getView(R.id.iv_icon));
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mCallBack = listener;
    }

    public interface OnItemClickListener {
        void onMinusClick(int position, GoodsDetailInfo item);

        void onPlusClick(int position, GoodsDetailInfo item);
    }
}
