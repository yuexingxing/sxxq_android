package cn.sanshaoxingqiu.ssbm.module.personal.account.view;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.Res;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityBindPhoneBinding;
import cn.sanshaoxingqiu.ssbm.module.login.bean.LoginResponse;
import cn.sanshaoxingqiu.ssbm.module.login.model.ILoginCallBack;
import cn.sanshaoxingqiu.ssbm.module.login.viewmodel.LoginViewModel;
import cn.sanshaoxingqiu.ssbm.module.personal.bean.UserInfo;
import cn.sanshaoxingqiu.ssbm.util.CommandTools;
import cn.sanshaoxingqiu.ssbm.util.LoadDialogMgr;
import cn.sanshaoxingqiu.ssbm.util.ToastUtil;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

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
                if (TextUtils.isEmpty(mPhone)) {
                    ToastUtil.showShortToast("手机号不能为空");
                    return;
                }
                String code = binding.edtCode.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showShortToast("验证码不能为空");
                    return;
                }
                LoadDialogMgr.getInstance().show(context, "提交中...");
                mViewModel.modifyPhone(mPhone, code);
            }
        });
        binding.tvGetCode.setOnClickListener(v -> {
            mPhone = binding.edtPhone.getText().toString();
            if (!CommandTools.isMobileNum(mPhone)) {
                ToastUtil.showShortToast("请输入正确的手机号");
                return;
            }
            LoadDialogMgr.getInstance().show(context);
            mViewModel.getSMSCode(mPhone, LoginViewModel.LoginType.CHANGE_PHONE);
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

    }

    @Override
    public void onLoginFailed() {

    }

    @Override
    public void onModifyPhone(String phone) {
        UserInfo userInfo = SSApplication.getInstance().getUserInfo();
        userInfo.mem_phone = phone;
        SSApplication.getInstance().saveUserInfo(userInfo);
        ToastUtil.showShortToast("修改成功");
        finish();
    }
}