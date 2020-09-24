package cn.sanshaoxingqiu.ssbm.module.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.GlideUtil;
import com.exam.commonbiz.util.Res;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityLoginBinding;
import cn.sanshaoxingqiu.ssbm.module.EmptyWebViewActivity;
import cn.sanshaoxingqiu.ssbm.module.MainActivity;
import cn.sanshaoxingqiu.ssbm.module.login.bean.LoginResponse;
import cn.sanshaoxingqiu.ssbm.module.login.model.ILoginCallBack;
import cn.sanshaoxingqiu.ssbm.module.login.viewmodel.LoginViewModel;
import cn.sanshaoxingqiu.ssbm.module.personal.account.view.BindWeChatActivity;

import com.exam.commonbiz.bean.UserInfo;

import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.ExerciseActivity;
import cn.sanshaoxingqiu.ssbm.util.CommandTools;
import cn.sanshaoxingqiu.ssbm.util.Constants;

import com.exam.commonbiz.util.LoadDialogMgr;
import com.exam.commonbiz.util.ToastUtil;

/**
 * 登录
 *
 * @Author yuexingxing
 * @time 2020/6/11
 */
public class LoginActivity extends BaseActivity<LoginViewModel, ActivityLoginBinding> implements ILoginCallBack {
    private final String TAG = LoginActivity.class.getSimpleName();
    public static final int FROM_PAGE_HOME = 0;
    public static final int FROM_PAGE_EXERCISE = 1;
    private String mPhone;
    private String mInviteId;
    private int mFromPage;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    public static void start(Context context, int fromPage) {
        Intent starter = new Intent(context, LoginActivity.class);
        starter.putExtra(Constants.OPT_DATA, fromPage);
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

        if (getIntent() != null && getIntent().hasExtra(Constants.OPT_DATA)) {
            mFromPage = getIntent().getIntExtra(Constants.OPT_DATA, FROM_PAGE_HOME);
        }
        mViewModel.setCallBack(this);
        binding.tvJump.setOnClickListener(v -> {
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
        binding.tvGetCode.setOnClickListener(v -> {
            mPhone = binding.edtPhone.getText().toString();
            if (!CommandTools.isMobileNum(mPhone)) {
                ToastUtil.showShortToast("请输入正确的手机号");
                return;
            }
            LoadDialogMgr.getInstance().show(context);
            mViewModel.getSMSCode(mPhone, LoginViewModel.LoginType.APP_LOGIN);
        });
        binding.tvLogin.setOnClickListener(v -> {
            login();
        });
        binding.includePolicy.tvAgreement.setOnClickListener(v -> EmptyWebViewActivity.start(context, "http://www.baidu.com"));
        binding.includePolicy.tvPolicy.setOnClickListener(v -> EmptyWebViewActivity.start(context, "http://www.2345.com"));
        binding.rlLoginWechat.setOnClickListener(view -> BindWeChatActivity.start(context));

        binding.edtInviteCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 6) {
                    mViewModel.getMemInfoByInvitationCode(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
            binding.tvGetCode.setText((millisUntilFinished / 1000) + "s");
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
        if (loginResponse == null) {
            return;
        }
        SSApplication.setToken(loginResponse.token);
        if (FROM_PAGE_EXERCISE == mFromPage) {
            finish();
        } else {
            MainActivity.start(context);
        }
    }

    @Override
    public void onLoginFailed() {

    }

    @Override
    public void onModifyPhone(String phone) {

    }

    @Override
    public void onMemInfoByInvitationCode(UserInfo userInfo) {
        binding.flInvite.setVisibility(View.INVISIBLE);
        if (userInfo == null) {
            return;
        }
        mInviteId = userInfo.mem_id;
        binding.flInvite.setVisibility(View.VISIBLE);
        binding.tvInviteName.setText(userInfo.nickname);
        binding.tvInviteCode.setText("邀请码: " + userInfo.invitation_code);
        GlideUtil.loadAvatar(userInfo.avatar, binding.ivInviteAvatar);
    }

    private void login() {
        mPhone = binding.edtPhone.getText().toString();
        String code = binding.edtCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showShortToast("验证码不能为空");
            return;
        }
        if (!binding.includePolicy.checkbox.isChecked()) {
            ToastUtil.showShortToast("未勾选协议和隐私政策");
            return;
        }
        LoadDialogMgr.getInstance().show(context, "登录中...");
        if (TextUtils.isEmpty(mInviteId)) {
            mInviteId = "";
        }
        mViewModel.login(mPhone, code, mInviteId);
    }
}