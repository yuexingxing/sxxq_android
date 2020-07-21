package com.sanshao.bs.module.personal.view;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.ActivitySettingBinding;
import com.sanshao.bs.databinding.ActivitySettingNameBinding;
import com.sanshao.bs.module.personal.bean.UserInfo;
import com.sanshao.bs.module.personal.view.account.view.AccountSafetyActivity;
import com.sanshao.bs.module.personal.viewmodel.SettingNameViewModel;
import com.sanshao.bs.util.Constants;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

/**
 * 设置
 *
 * @Author yuexingxing
 * @time 2020/7/9
 */
public class SettingActivity extends BaseActivity<SettingNameViewModel, ActivitySettingBinding> {

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
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
        binding.pavAccount.setOnClickListener(v -> AccountSafetyActivity.start(context));
        binding.pavAboutUs.setOnClickListener(v -> AboutUsActivity.start(context));
    }
}