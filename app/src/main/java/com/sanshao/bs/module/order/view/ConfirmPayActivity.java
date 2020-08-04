package com.sanshao.bs.module.order.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityConfirmPayBinding;
import com.sanshao.bs.module.order.bean.ConfirmOrderResponse;
import com.sanshao.bs.module.order.bean.CreateOrderResponse;
import com.sanshao.bs.module.order.bean.OrderPayInfoResponse;
import com.sanshao.bs.module.order.event.PayStatusChangedEvent;
import com.sanshao.bs.module.order.model.IConfirmOrderModel;
import com.sanshao.bs.module.order.model.IConfirmPayModel;
import com.sanshao.bs.module.order.model.OnPayListener;
import com.sanshao.bs.module.order.util.PayUtils;
import com.sanshao.bs.module.order.viewmodel.ConfirmOrderViewModel;
import com.sanshao.bs.module.order.viewmodel.ConfirmPayViewModel;
import com.sanshao.bs.util.Constants;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 确认付款
 *
 * @Author yuexingxing
 * @time 2020/6/20
 */
public class ConfirmPayActivity extends BaseActivity<ConfirmPayViewModel, ActivityConfirmPayBinding> implements IConfirmPayModel, IConfirmOrderModel {
    private final int PAY_BY_WECHAT = 0;
    private final int PAY_BY_ALI = 1;
    private int mPayType = PAY_BY_WECHAT;

    public static void start(Context context, CreateOrderResponse createOrderResponse) {
        Intent starter = new Intent(context, ConfirmPayActivity.class);
        starter.putExtra(Constants.OPT_DATA, createOrderResponse);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_pay;
    }

    @Override
    public void initData() {

        CreateOrderResponse createOrderResponse = (CreateOrderResponse) getIntent().getSerializableExtra(Constants.OPT_DATA);
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
        binding.tvOrderNo.setText("订单编号：" + createOrderResponse.orderNo);
        binding.tvPrice.setText(createOrderResponse.orderPrice);
        binding.btnStartPay.setOnClickListener(v -> mViewModel.getOrderPayInfo(mPayType));
        binding.llPayWechat.setOnClickListener(v -> setCheckStatus(PAY_BY_WECHAT));
        binding.llPayAli.setOnClickListener(v -> setCheckStatus(PAY_BY_ALI));
        binding.checkWechat.setOnClickListener(v -> setCheckStatus(PAY_BY_WECHAT));
        binding.checkAlipay.setOnClickListener(v -> setCheckStatus(PAY_BY_ALI));
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

    /**
     * @param payType 0-微信支付 1-支付宝支付
     */
    private void setCheckStatus(int payType) {
        mPayType = payType;
        if (PAY_BY_WECHAT == payType) {
            binding.checkWechat.setImageResource(R.drawable.icon_login_checked);
            binding.checkAlipay.setImageResource(R.drawable.icon_login_unchecked);
        } else {
            binding.checkAlipay.setImageResource(R.drawable.icon_login_checked);
            binding.checkWechat.setImageResource(R.drawable.icon_login_unchecked);
        }
    }

    @Override
    public void returnOrderPayInfo(OrderPayInfoResponse orderPayInfoResponse) {
        //TODO 获取支付信息，发起支付
        PayCompleteActivity.start(context);
        if (orderPayInfoResponse == null) {
            return;
        }

        PayUtils payUtils = new PayUtils();
        if (PAY_BY_WECHAT == mPayType) {
            payUtils.wxPay(ConfirmPayActivity.this, orderPayInfoResponse.orderInfo);
        } else {
            payUtils.aliPay(ConfirmPayActivity.this, orderPayInfoResponse.orderInfo);
        }
        payUtils.setOnPayListener(new OnPayListener() {
            @Override
            public void onPaySuccess() {
                PayCompleteActivity.start(context);
            }

            @Override
            public void onPayFailed() {

            }
        });
    }

    @Override
    public void returnCreateOrderInfo(CreateOrderResponse createOrderResponse) {

    }

    @Override
    public void returnConfirmOrder(ConfirmOrderResponse confirmOrderResponse) {

    }

    @Override
    public void returnSubmitOrderInfo() {

    }
}