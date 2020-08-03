package com.sanshao.bs.module.personal.account.view;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.Res;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.module.login.bean.LoginResponse;
import com.sanshao.bs.module.login.model.ILoginCallBack;
import com.sanshao.bs.module.login.viewmodel.LoginViewModel;
import com.sanshao.bs.util.CommandTools;
import com.sanshao.bs.util.LoadDialogMgr;
import com.sanshao.bs.util.ToastUtil;
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
public class BindPhoneActivity extends BaseActivity<LoginViewModel, ActivityBindPhoneBinding> implements ILoginCallBack {

    private String mPhone;

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
                String code = binding.edtCode.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showShortToast("验证码不能为空");
                    return;
                }
                LoadDialogMgr.getInstance().show(context, "提交中...");
                mViewModel.login(mPhone, code, "123");
            }
        });
        binding.tvGetCode.setOnClickListener(v -> {
            mPhone = binding.edtPhone.getText().toString();
            if (!CommandTools.isMobileNum(mPhone)) {
                ToastUtil.showShortToast("请输入正确的手机号");
                return;
            }
            LoadDialogMgr.getInstance().show(context);
            mViewModel.getSMSCode(mPhone, "1");
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
    public void onLoginSuccess(LoginResponse loginResponse) {

    }

    @Override
    public void onLoginFailed() {

    }
}