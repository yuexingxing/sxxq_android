package cn.sanshaoxingqiu.ssbm.module.order.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityConfirmPayBinding;
import cn.sanshaoxingqiu.ssbm.module.order.bean.ConfirmOrderResponse;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderPayInfoResponse;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderStatusResponse;
import cn.sanshaoxingqiu.ssbm.module.order.event.PayStatusChangedEvent;
import cn.sanshaoxingqiu.ssbm.module.order.model.IConfirmOrderModel;
import cn.sanshaoxingqiu.ssbm.module.order.model.IPayModel;
import cn.sanshaoxingqiu.ssbm.module.order.model.OnPayListener;
import cn.sanshaoxingqiu.ssbm.module.order.util.PayUtils;
import cn.sanshaoxingqiu.ssbm.module.order.viewmodel.PayViewModel;
import cn.sanshaoxingqiu.ssbm.module.personal.bean.UserInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.util.CommandTools;
import cn.sanshaoxingqiu.ssbm.util.Constants;
import cn.sanshaoxingqiu.ssbm.util.ShareUtils;
import cn.sanshaoxingqiu.ssbm.util.ToastUtil;

import com.exam.commonbiz.base.BaseActivity;
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
    public static final String PAY_BY_WECHAT = "HFWX";
    public static final String PAY_BY_ALI_APP = "HFALIPAYAPP";
    private String mPayType = PAY_BY_ALI_APP;//默认支付宝支付
    private boolean isFirstIn = true;
    private UserInfo mUserInfo;
    private GoodsDetailInfo mGoodsDetailInfo;

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

        mGoodsDetailInfo = (GoodsDetailInfo) getIntent().getSerializableExtra(Constants.OPT_DATA);
        if (mGoodsDetailInfo == null) {
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
        binding.tvPrice.setText(mGoodsDetailInfo.getPriceText());
        binding.tvName.setText(mGoodsDetailInfo.sarti_name);
        binding.tvOrderNo.setText("订单编号：" + mGoodsDetailInfo.salebill_id);
        mUserInfo = SSApplication.getInstance().getUserInfo();
        if (mGoodsDetailInfo.isFree() && mUserInfo.hasBenefitsRight()) {
            binding.rlPay.setVisibility(View.INVISIBLE);
            binding.btnStartPay.setText("免费领取");
        } else if (mGoodsDetailInfo.isPayByPoint()) {
            binding.rlPay.setVisibility(View.INVISIBLE);
            binding.btnStartPay.setText("确认支付");
        } else {
            binding.rlPay.setVisibility(View.VISIBLE);
        }
        binding.btnStartPay.setOnClickListener(v -> {
            if (TextUtils.equals(mPayType, PAY_BY_WECHAT)) {
                String path = "/pages/order/appPay?" + "salebill_id=" + mGoodsDetailInfo.salebill_id
                        + "&mem_phone=" + mUserInfo.mem_phone + "&benefits_level=" + mUserInfo.benefits_level;
                new ShareUtils()
                        .init(context)
                        .jump2WxMiniProgram(path);
            } else {
                mViewModel.getOrderPayInfo(PayViewModel.GET_PAY_INFO, mGoodsDetailInfo.salebill_id, mPayType);
            }
        });
        setCheckStatus(mPayType);
        binding.llPayWechat.setOnClickListener(v -> setCheckStatus(PAY_BY_WECHAT));
        binding.llPayAli.setOnClickListener(v -> setCheckStatus(PAY_BY_ALI_APP));
        binding.checkWechat.setOnClickListener(v -> setCheckStatus(PAY_BY_WECHAT));
        binding.checkAlipay.setOnClickListener(v -> setCheckStatus(PAY_BY_ALI_APP));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirstIn) {
            mViewModel.getOrderPayInfo(PayViewModel.CHECK_ORDER_STATUS, mGoodsDetailInfo.salebill_id, mPayType);
        }
        isFirstIn = false;
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
            mPayType = PAY_BY_ALI_APP;
        }
    }

    @Override
    public void returnOrderPayInfo(int optType, OrderPayInfoResponse orderPayInfoResponse) {
        if (orderPayInfoResponse == null) {
            ToastUtil.showShortToast("支付成功");
            PayCompleteActivity.start(context, mGoodsDetailInfo.sarti_id, mGoodsDetailInfo.salebill_id);
            return;
        }

        PayUtils payUtils = new PayUtils();
        payUtils.registerApp(context);
        payUtils.setOnPayListener(new OnPayListener() {
            @Override
            public void onPaySuccess() {

            }

            @Override
            public void onPayFailed() {
                ToastUtil.showShortToast("支付失败");
            }
        });
        payUtils.startPay(ConfirmPayActivity.this, mPayType, CommandTools.beanToJson(orderPayInfoResponse));
    }

    @Override
    public void returnOrderStatus(OrderStatusResponse orderStatusResponse) {
        if (orderStatusResponse == null) {
            return;
        }

    }

    @Override
    public void returnFVipPay(int optType, OrderPayInfoResponse orderPayInfoResponse) {

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