package com.sanshao.bs.module;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.bs.module.order.bean.OrderInfo;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.livemodule.zhibo.login.TCLoginActivity;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityTestMenuBinding;
import com.sanshao.bs.module.login.view.LoginActivity;
import com.sanshao.bs.module.order.view.OrderListActivity;
import com.sanshao.bs.wxapi.alipay.AliPayDemoActivity;

/**
 * 调试模块
 *
 * @Author yuexingxing
 * @time 2020/6/19
 */
public class TestMenuActivity extends BaseActivity<TestMenuViewModel, ActivityTestMenuBinding> {

    public static void start(Context context) {
        Intent starter = new Intent(context, TestMenuActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_menu;
    }

    @Override
    public void initData() {

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

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.start(TestMenuActivity.this);
            }
        });

        binding.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AliPayDemoActivity.start(context);
            }
        });

        binding.btnLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TCLoginActivity.class);
                startActivity(intent);
            }
        });

        binding.btnOrderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderListActivity.start(context, OrderInfo.State.ALL);
            }
        });
    }
}