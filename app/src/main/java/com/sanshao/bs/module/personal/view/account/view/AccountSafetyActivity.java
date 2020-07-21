package com.sanshao.bs.module.personal.view.account.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityAccountSafetyBinding;
import com.sanshao.bs.module.personal.view.SettingAccountActivity;
import com.sanshao.bs.module.personal.view.account.viewmodel.AccountSafetyViewModel;

/**
 * 账户安全
 *
 * @Author yuexingxing
 * @time 2020/7/2
 */
public class AccountSafetyActivity extends BaseActivity<AccountSafetyViewModel, ActivityAccountSafetyBinding> {

    public static void start(Context context) {
        Intent starter = new Intent(context, AccountSafetyActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_safety;
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
        binding.tvBindphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BindPhoneActivity.start(context);
            }
        });
        binding.tvSettingpsd.setOnClickListener(v -> {
            SettingAccountActivity.start(context, SettingAccountActivity.SETTING_PASSWORD);
        });
    }
}