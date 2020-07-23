package com.sanshao.bs.module.personal.account.view;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.util.Res;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.ActivityVerifyPhoneBinding;
import com.sanshao.bs.module.login.model.ILoginCallBack;
import com.sanshao.bs.module.login.view.LoginActivity;
import com.sanshao.bs.module.login.viewmodel.LoginViewModel;
import com.sanshao.bs.util.LoadDialogMgr;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

/**
 * 验证手机
 *
 * @Author yuexingxing
 * @time 2020/7/23
 */
public class VerifyPhoneActivity extends BaseActivity<BaseViewModel, ActivityVerifyPhoneBinding> implements ILoginCallBack {

    private LoginViewModel mLoginViewModel;

    public static void start(Context context) {
        Intent starter = new Intent(context, VerifyPhoneActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_verify_phone;
    }

    @Override
    public void initData() {

        mLoginViewModel = new LoginViewModel();
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
                BindPhoneActivity.start(context);
            }
        });

        binding.tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadDialogMgr.getInstance().show(context);
                mLoginViewModel.getSMSCode("1234567890", "1");
            }
        });
    }

    /**
     * 取消计时
     */
    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * 计时器
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            binding.tvGetCode.setText(String.valueOf((millisUntilFinished / 1000) + "s"));
            binding.tvGetCode.setTextColor(Res.getColor(SSApplication.app, R.color.color_b6a578));
        }

        @Override
        public void onFinish() {
            binding.tvGetCode.setEnabled(true);
            binding.tvGetCode.setText("重新发送");
            binding.tvGetCode.setTextColor(Res.getColor(SSApplication.app, R.color.color_333333));
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }

    @Override
    public void onGetCode() {
        timer.start();
        binding.tvGetCode.setEnabled(false);
    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFailed() {

    }
}