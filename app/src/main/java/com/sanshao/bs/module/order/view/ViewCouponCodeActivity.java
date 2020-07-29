package com.sanshao.bs.module.order.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityViewCouponCodeBinding;
import com.sanshao.bs.module.order.bean.OrderDetailResponse;
import com.sanshao.bs.module.order.model.IOrderDetailModel;
import com.sanshao.bs.module.order.view.adapter.ConfirmOrderAdapter;
import com.sanshao.bs.module.order.viewmodel.OrderDetailViewModel;
import com.sanshao.bs.module.order.viewmodel.ViewCouponCodeViewModel;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

/**
 * 查看券码
 *
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class ViewCouponCodeActivity extends BaseActivity<OrderDetailViewModel, ActivityViewCouponCodeBinding> implements IOrderDetailModel {

    public static void start(Context context) {
        Intent starter = new Intent(context, ViewCouponCodeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_coupon_code;
    }

    @Override
    public void initData() {

        mViewModel.setCallBack(this);
        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });

        binding.mulitySetMealView.setOptType(ConfirmOrderAdapter.OPT_TYPE_ORDER_DETAIL);
        binding.mulitySetMealView.setFragmentManager(getSupportFragmentManager());
        mViewModel.getOrderDetailInfo(1);
    }

    @Override
    public void returnOrderDetailInfo(OrderDetailResponse orderDetailResponse) {
        if (orderDetailResponse == null) {
            return;
        }
        binding.mulitySetMealView.mConfirmOrderAdapter.addData(orderDetailResponse.goodsDetailInfo);
    }
}