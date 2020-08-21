package com.sanshao.bs.module.order.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityConfirmPayBinding;
import com.sanshao.bs.module.order.bean.ConfirmOrderResponse;
import com.sanshao.bs.module.order.bean.OrderPayInfoResponse;
import com.sanshao.bs.module.order.bean.OrderStatusResponse;
import com.sanshao.bs.module.order.event.PayStatusChangedEvent;
import com.sanshao.bs.module.order.model.IConfirmOrderModel;
import com.sanshao.bs.module.order.model.IPayModel;
import com.sanshao.bs.module.order.model.OnPayListener;
import com.sanshao.bs.module.order.util.PayUtils;
import com.sanshao.bs.module.order.viewmodel.PayViewModel;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.util.CommandTools;
import com.sanshao.bs.util.Constants;
import com.sanshao.bs.util.ShareUtils;
import com.sanshao.bs.util.ToastUtil;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 确认付款
 *
 * @Author yuexingxing
 * @time 2020/6/20
 */
public class ConfirmPayActivity extends BaseActivity<PayViewModel, ActivityConfirmPayBinding> implements IPayModel, IConfirmOrderModel {
    private final String PAY_BY_WECHAT = "HFWX";
    private final String PAY_BY_ALI_APP = "HFALIPAYAPP";
    private final String PAY_BY_ALI_H5 = "HFALIPAYWAP";
    private String mPayType = PAY_BY_ALI_APP;
    private String mSalebillId;

    public static void start(Context context, GoodsDetailInfo goodsDetailInfo) {
        Intent starter = new Intent(context, ConfirmPayActivity.class);
        starter.putExtra(Constants.OPT_DATA, goodsDetailInfo);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_pay;
    }

    @Override
    public void initData() {

        GoodsDetailInfo goodsDetailInfo = (GoodsDetailInfo) getIntent().getSerializableExtra(Constants.OPT_DATA);
        if (goodsDetailInfo == null) {
            finish();
        }
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
        mSalebillId = goodsDetailInfo.salebill_id;
        binding.tvPrice.setText(goodsDetailInfo.getPriceText());
        binding.tvName.setText(goodsDetailInfo.sarti_name);
        binding.tvOrderNo.setText("订单编号：" + goodsDetailInfo.salebill_id);
        if (goodsDetailInfo.isFree()) {
            binding.rlPay.setVisibility(View.INVISIBLE);
            binding.btnStartPay.setText("免费领取");
        } else if (goodsDetailInfo.isPayByPoint()) {
            binding.rlPay.setVisibility(View.INVISIBLE);
            binding.btnStartPay.setText("确认支付");
        } else {
            binding.rlPay.setVisibility(View.VISIBLE);
        }
        binding.btnStartPay.setOnClickListener(v -> {
            if (TextUtils.equals(mPayType, PAY_BY_WECHAT)) {
                String path = "pages/order/confirmPay?" + "salebillId=" + goodsDetailInfo.salebill_id;
                ShareUtils.jump2WxMiniProgram(context, path);
            } else {
                mViewModel.getOrderPayInfo(PayViewModel.GET_PAY_INFO, mSalebillId, mPayType);
            }
        });
        setCheckStatus(PAY_BY_ALI_APP);//默认支付宝支付
        binding.llPayWechat.setOnClickListener(v -> setCheckStatus(PAY_BY_WECHAT));
        binding.llPayAli.setOnClickListener(v -> setCheckStatus(PAY_BY_ALI_APP));
        binding.checkWechat.setOnClickListener(v -> setCheckStatus(PAY_BY_WECHAT));
        binding.checkAlipay.setOnClickListener(v -> setCheckStatus(PAY_BY_ALI_APP));
    }

    @Override
    protected void onResume() {
        super.onResume();

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
    private void setCheckStatus(String payType) {
        mPayType = payType;
        if (TextUtils.equals(PAY_BY_WECHAT, payType)) {
            binding.checkWechat.setImageResource(R.drawable.icon_login_checked);
            binding.checkAlipay.setImageResource(R.drawable.icon_login_unchecked);
        } else {
            binding.checkAlipay.setImageResource(R.drawable.icon_login_checked);
            binding.checkWechat.setImageResource(R.drawable.icon_login_unchecked);
            if (ShareUtils.checkAliPayInstalled(context)) {
                mPayType = PAY_BY_ALI_APP;
            } else {
                mPayType = PAY_BY_ALI_H5;
            }
        }
    }

    @Override
    public void returnOrderPayInfo(int optType, OrderPayInfoResponse orderPayInfoResponse) {
        if (PayViewModel.CHECK_PRDER_STATUS == optType && orderPayInfoResponse == null) {
            ToastUtil.showShortToast("支付成功");
            PayCompleteActivity.start(context, mSalebillId);
            return;
        }

        if (orderPayInfoResponse == null) {
            return;
        }

        PayUtils payUtils = new PayUtils();
        payUtils.registerApp(context);
        payUtils.setOnPayListener(new OnPayListener() {
            @Override
            public void onPaySuccess() {
                mViewModel.getOrderPayInfo(PayViewModel.CHECK_PRDER_STATUS, mSalebillId, mPayType);
            }

            @Override
            public void onPayFailed() {
                ToastUtil.showShortToast("支付失败");
            }
        });
        payUtils.startPay(ConfirmPayActivity.this, CommandTools.beanToJson(orderPayInfoResponse));
    }

    @Override
    public void returnOrderStatus(OrderStatusResponse orderStatusResponse) {
        if (orderStatusResponse == null) {
            return;
        }
    }

    @Override
    public void returnCreateOrderInfo(GoodsDetailInfo goodsDetailInfo) {

    }

    @Override
    public void returnConfirmOrder(ConfirmOrderResponse confirmOrderResponse) {

    }

    @Override
    public void returnSubmitOrderInfo() {

    }
}