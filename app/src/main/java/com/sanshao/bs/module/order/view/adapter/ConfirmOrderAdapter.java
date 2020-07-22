package com.sanshao.bs.module.order.view.adapter;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.util.ContainerUtil;
import com.exam.commonbiz.util.ScreenUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.module.order.view.ViewCouponCodeFragment;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
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

    public void setOptType(int optType){
        mOptType = optType;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailInfo item) {
        helper.setText(R.id.tv_title, helper.getAdapterPosition() + "-" + item.name);
        helper.setText(R.id.tv_buy_count, item.buyNum + "");
        helper.setText(R.id.tv_price, MathUtil.getNumExclude0(item.price));

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
        Glide.with(SSApplication.app).load(item.icon).into((ImageView) helper.getView(R.id.iv_icon));

        FrameLayout flSetMeal = helper.getView(R.id.fl_set_meal);
        LinearLayout llOpenSetMeal = helper.getView(R.id.ll_more_setmeal);
        LinearLayout llCloseSetMeal = helper.getView(R.id.ll_close_setmeal);

        llOpenSetMeal.setOnClickListener(v->{
            llOpenSetMeal.setVisibility(View.GONE);
            flSetMeal.setVisibility(View.VISIBLE);
        });
        llCloseSetMeal.setOnClickListener(v->{
            flSetMeal.setVisibility(View.GONE);
            llOpenSetMeal.setVisibility(View.VISIBLE);
        });

        helper.getView(R.id.include_goods_single).setVisibility(View.GONE);
        helper.getView(R.id.ll_mulity_set_meal).setVisibility(View.GONE);
        helper.getView(R.id.tv_goods_count).setVisibility(View.GONE);

        LinearLayout llConentTop = helper.getView(R.id.ll_content_top);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llConentTop.getLayoutParams();
        //确认订单
        if (mOptType == OPT_TYPE_CONFIRM_ORDER){
            helper.getView(R.id.ll_right_price).setVisibility(View.GONE);
            helper.getView(R.id.ll_count_view).setVisibility(View.VISIBLE);
        }else if (mOptType == OPT_TYPE_ORDER_DETAIL){
            helper.getView(R.id.ll_right_price).setVisibility(View.VISIBLE);
            helper.getView(R.id.include_goods_single).setVisibility(View.VISIBLE);
            helper.getView(R.id.ll_mulity_set_meal).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_goods_count).setVisibility(View.VISIBLE);
            helper.getView(R.id.ll_count_view).setVisibility(View.GONE);

            layoutParams.leftMargin = ScreenUtil.dp2px(SSApplication.app, 12);
            layoutParams.rightMargin = ScreenUtil.dp2px(SSApplication.app, 12);
            llConentTop.setLayoutParams(layoutParams);
        }else if (mOptType == OPT_TYPE_APPOINTMENT){
            helper.getView(R.id.ll_right_price).setVisibility(View.GONE);
            helper.getView(R.id.ll_count_view).setVisibility(View.GONE);
        }

        //如果有套餐
        if (!ContainerUtil.isEmpty(item.setMealList)){
            if (flSetMeal.getVisibility() == View.GONE){
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
            setMealAdapter.setNewData(item.setMealList);
        }else{
            llOpenSetMeal.setVisibility(View.GONE);
        }

        if (mFragmentManager != null){
            List<Fragment> mFragmentList = new ArrayList<>();
            for (int i = 0; i <5 ; i++) {
                mFragmentList.add(ViewCouponCodeFragment.newInstance(i));
            }
            ViewPager viewPager = helper.getView(R.id.view_pager);
            TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(mFragmentManager, mFragmentList);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(0);
            viewPager.setOffscreenPageLimit(mFragmentList.size());
        }
    }

    public void setFragmentManager(FragmentManager fragmentManager){
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
