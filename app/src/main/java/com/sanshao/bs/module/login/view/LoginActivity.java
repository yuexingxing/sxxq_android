package com.sanshao.bs.module.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.log.XLog;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.Res;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.ActivityLoginBinding;
import com.sanshao.bs.module.EmptyWebViewActivity;
import com.sanshao.bs.module.MainActivity;
import com.sanshao.bs.module.login.bean.AuthInfo;
import com.sanshao.bs.module.login.bean.LoginBean;
import com.sanshao.bs.module.login.model.ILoginCallBack;
import com.sanshao.bs.module.login.viewmodel.LoginViewModel;
import com.sanshao.bs.util.CommandTools;
import com.sanshao.bs.util.LoadDialogMgr;
import com.sanshao.bs.util.ToastUtil;
import com.sanshao.bs.util.UMSocialUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 登录
 *
 * @Author yuexingxing
 * @time 2020/6/11
 */
public class LoginActivity extends BaseActivity<LoginViewModel, ActivityLoginBinding> implements ILoginCallBack {
    private final String TAG = LoginActivity.class.getSimpleName();
    private String mPhone;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
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
    public int getStatusBarColor() {
        return R.color.main_bg;
    }

    @Override
    public void initData() {
        LoginBean loginBean = new LoginBean();
        loginBean.phone = "18744556665";
        loginBean.code = "12345";
        binding.setUser(loginBean);
        binding.tvJump.setOnClickListener(v->{
            MainActivity.start(context);
        });
        binding.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s) || s.length() < 11) {
                    binding.tvLogin.setEnabled(false);
                } else {
                    binding.tvLogin.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhone = binding.edtPhone.getText().toString();
                if (!CommandTools.isMobileNum(mPhone)) {
                    ToastUtil.showShortToast("请输入正确的手机号");
                    return;
                }
                LoadDialogMgr.getInstance().show(context);
                mViewModel.getSMSCode(mPhone, "1");
            }
        });
        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = binding.edtCode.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showShortToast("验证码不能为空");
                    return;
                }
                LoadDialogMgr.getInstance().show(context, "登录中...");
                mViewModel.login(mPhone, code);
            }
        });
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
        binding.rlLoginWechat.setOnClickListener(view -> {
            UMSocialUtil.authorization(context, SHARE_MEDIA.WEIXIN, new CommonCallBack() {
                @Override
                public void callback(int postion, Object object) {
                    if (postion == 0 && object != null){
                        AuthInfo authInfo = (AuthInfo) object;
                        XLog.d(TAG, authInfo.openid);
                    }
                }
            });
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
        ToastUtil.showShortToast("onLoginResponse");
    }

    @Override
    public void onLoginFailed() {

    }
}