package cn.sanshaoxingqiu.ssbm.module.personal.account.view;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityVerifyPhoneBinding;
import cn.sanshaoxingqiu.ssbm.module.login.bean.LoginResponse;
import cn.sanshaoxingqiu.ssbm.module.login.model.ILoginCallBack;
import cn.sanshaoxingqiu.ssbm.module.login.viewmodel.LoginViewModel;

import com.exam.commonbiz.bean.UserInfo;
import cn.sanshaoxingqiu.ssbm.util.Constants;
import cn.sanshaoxingqiu.ssbm.util.LoadDialogMgr;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.Res;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

/**
 * 验证手机
 *
 * @Author yuexingxing
 * @time 2020/7/23
 */
public class VerifyPhoneActivity extends BaseActivity<LoginViewModel, ActivityVerifyPhoneBinding> implements ILoginCallBack {

    private String mPhone;

    public static void start(Context context, String phone) {
        Intent starter = new Intent(context, VerifyPhoneActivity.class);
        starter.putExtra(Constants.OPT_DATA, phone);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_verify_phone;
    }

    @Override
    public void initData() {

        mPhone = getIntent().getStringExtra(Constants.OPT_DATA);
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
                BindPhoneActivity.start(context);
            }
        });

        binding.tvGetCode.setOnClickListener(v -> {
            LoadDialogMgr.getInstance().show(context);
            mViewModel.getSMSCode(mPhone, LoginViewModel.LoginType.CHANGE_PHONE);
        });

        binding.tvPhone.setText("已绑定手机：" + mPhone);
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

    }

    @Override
    public void onMemInfoByInvitationCode(UserInfo userInfo) {

    }
}