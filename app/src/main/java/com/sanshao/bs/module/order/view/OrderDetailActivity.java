package com.sanshao.bs.module.order.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityOrderDetailBinding;
import com.sanshao.bs.module.order.bean.OrderDetailResponse;
import com.sanshao.bs.module.order.bean.OrderNumStatusResponse;
import com.sanshao.bs.module.order.event.PayStatusChangedEvent;
import com.sanshao.bs.module.order.model.IOrderDetailModel;
import com.sanshao.bs.module.order.view.adapter.ConfirmOrderAdapter;
import com.sanshao.bs.module.order.view.adapter.RemainingServiceAdapter;
import com.sanshao.bs.module.order.view.adapter.ServedAdapter;
import com.sanshao.bs.module.order.viewmodel.OrderDetailViewModel;
import com.sanshao.bs.util.CommandTools;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 订单详情
 *
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class OrderDetailActivity extends BaseActivity<OrderDetailViewModel, ActivityOrderDetailBinding> implements IOrderDetailModel {

    private ServedAdapter mServedAdapter;
    private RemainingServiceAdapter mRemainingServiceAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, OrderDetailActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
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

        mServedAdapter = new ServedAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerViewServed.setLayoutManager(linearLayoutManager);
        binding.recyclerViewServed.setAdapter(mServedAdapter);
        binding.recyclerViewServed.setNestedScrollingEnabled(false);
        binding.recyclerViewServed.setFocusable(false);

        mRemainingServiceAdapter = new RemainingServiceAdapter();
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context);
        linearLayoutManager2.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerViewRemainingService.setLayoutManager(linearLayoutManager2);
        binding.recyclerViewRemainingService.setAdapter(mRemainingServiceAdapter);
        binding.recyclerViewRemainingService.setNestedScrollingEnabled(false);
        binding.recyclerViewRemainingService.setFocusable(false);
        binding.tvCopy.setOnClickListener(view -> CommandTools.copyToClipboard(context, binding.tvOrderNo.getText().toString()));
        binding.llOrderMore.setOnClickListener(view -> {
            if (binding.recyclerViewServed.getVisibility() == View.GONE) {
                binding.ivMore.setImageResource(R.drawable.icon_goods_detail_more);
                binding.recyclerViewServed.setVisibility(View.VISIBLE);
                binding.recyclerViewRemainingService.setVisibility(View.VISIBLE);
            } else {
                binding.ivMore.setImageResource(R.drawable.icon_goods_detail_down);
                binding.recyclerViewServed.setVisibility(View.GONE);
                binding.recyclerViewRemainingService.setVisibility(View.GONE);
            }
        });

        binding.llCall.setOnClickListener(view -> {
            CommandTools.callPhone(context, "1234567");
        });

        binding.mulitySetMealView.setFragmentManager(getSupportFragmentManager());
        binding.mulitySetMealView.setOptType(ConfirmOrderAdapter.OPT_TYPE_ORDER_DETAIL);
        mViewModel.getOrderDetailInfo(1);
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayStatusChangedEvent(PayStatusChangedEvent payStatusChangedEvent) {
        if (payStatusChangedEvent == null) {
            return;
        }
        if (payStatusChangedEvent.paySuccess) {
            finish();
        }
    }

    @Override
    public void returnOrderDetailInfo(OrderDetailResponse orderDetailResponse) {
        if (orderDetailResponse == null) {
            return;
        }
        mServedAdapter.addData(orderDetailResponse.serverList);
        mRemainingServiceAdapter.addData(orderDetailResponse.remainingServerList);
        binding.mulitySetMealView.mConfirmOrderAdapter.addData(orderDetailResponse.goodsDetailInfo);
    }

    @Override
    public void returnOrderNumStatus(OrderNumStatusResponse orderNumStatusResponse) {

    }
}