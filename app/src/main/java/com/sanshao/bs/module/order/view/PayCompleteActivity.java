package com.sanshao.bs.module.order.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.bs.module.order.event.PayStatusChangedEvent;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityPayCompleteBinding;
import com.sanshao.bs.module.MainActivity;
import com.sanshao.bs.module.order.bean.OrderInfo;
import com.sanshao.bs.module.order.viewmodel.PayCompleteViewModel;
import com.sanshao.bs.module.shoppingcenter.view.adapter.GuessYouLoveAdapter;
import com.sanshao.bs.module.shoppingcenter.view.dialog.PaySuccessDialog;

import org.greenrobot.eventbus.EventBus;

/**
 * 支付完成
 *
 * @Author yuexingxing
 * @time 2020/6/20
 */
public class PayCompleteActivity extends BaseActivity<PayCompleteViewModel, ActivityPayCompleteBinding> {

    private GuessYouLoveAdapter mGuessYouLoveAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, PayCompleteActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_complete;
    }

    @Override
    public void initData() {

        PayStatusChangedEvent payStatusChangedEvent = new PayStatusChangedEvent();
        payStatusChangedEvent.paySuccess = true;
        EventBus.getDefault().post(payStatusChangedEvent);

        binding.llReward.getBackground().setAlpha(9);
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
        binding.tvToMain.setOnClickListener(v -> MainActivity.start(context));
        binding.tvViewOrder.setOnClickListener(v -> OrderListActivity.start(context,  OrderInfo.State.ALL));

        PaySuccessDialog paySuccessDialog = new PaySuccessDialog();
        paySuccessDialog.show(context);
    }
}