package com.sanshao.bs.module.personal.account.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.module.personal.bean.UserInfo;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityAccountSafetyBinding;
import com.sanshao.bs.module.personal.setting.view.SettingAccountActivity;
import com.sanshao.bs.module.personal.account.viewmodel.AccountSafetyViewModel;

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
    protected void onResume() {
        super.onResume();
        UserInfo userInfo = SSApplication.getInstance().getUserInfo();
        if (!TextUtils.isEmpty(userInfo.mem_phone)) {
            binding.tvPhone.setText(userInfo.mem_phone);
        }
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
        binding.llBindPhone.setOnClickListener(v -> {
            UserInfo userInfo = SSApplication.getInstance().getUserInfo();
            if (TextUtils.isEmpty(userInfo.mem_phone)) {
                BindPhoneActivity.start(context);
            } else {
                VerifyPhoneActivity.start(context, userInfo.mem_phone);
            }

        });
        binding.llBindWechat.setOnClickListener(v -> BindWeChatActivity.start(context));
    }
}