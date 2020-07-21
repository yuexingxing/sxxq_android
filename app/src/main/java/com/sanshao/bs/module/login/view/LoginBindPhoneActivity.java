package com.sanshao.bs.module.login.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityLoginBindPhoneBinding;
import com.sanshao.bs.module.EmptyWebViewActivity;


/**
 * 绑定手机号提示
 *
 * @Author yuexingxing
 * @time 2020/6/11
 */
public class LoginBindPhoneActivity extends BaseActivity<BaseViewModel, ActivityLoginBindPhoneBinding> {

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginBindPhoneActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_bind_phone;
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected boolean isUseBlackFontWithStatusBar() {
        return true;
    }

    @Override
    public void initData() {

        binding.includePolicy.tvAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmptyWebViewActivity.start(context, "http://www.baidu.com");
            }
        });
        binding.includePolicy.tvPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmptyWebViewActivity.start(context, "http://www.2345.com");
            }
        });
    }
}