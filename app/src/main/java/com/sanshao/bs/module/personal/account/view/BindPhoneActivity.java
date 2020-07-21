package com.sanshao.bs.module.personal.account.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityBindPhoneBinding;
import com.sanshao.bs.module.personal.account.viewmodel.BindPhoneViewModel;

/**
 * 绑定手机号
 *
 * @Author yuexingxing
 * @time 2020/7/2
 */
public class BindPhoneActivity extends BaseActivity<BindPhoneViewModel, ActivityBindPhoneBinding> {

    public static void start(Context context) {
        Intent starter = new Intent(context, BindPhoneActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_phone;
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
    }
}