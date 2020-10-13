package cn.sanshaoxingqiu.ssbm.module.splash;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.base.BasicApplication;
import com.exam.commonbiz.cache.ACache;
import com.exam.commonbiz.config.ConfigSP;
import com.exam.commonbiz.event.IdentityExpiredEvent;
import com.exam.commonbiz.kits.Kits;
import com.exam.commonbiz.net.HostUrl;
import com.exam.commonbiz.net.XApi;
import com.exam.commonbiz.util.AppManager;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityLaunchBinding;
import cn.sanshaoxingqiu.ssbm.module.MainActivity;
import cn.sanshaoxingqiu.ssbm.module.login.model.IVerfyApkModel;
import cn.sanshaoxingqiu.ssbm.module.login.view.LoginActivity;
import cn.sanshaoxingqiu.ssbm.module.login.viewmodel.LoginViewModel;
import cn.sanshaoxingqiu.ssbm.module.personal.event.UpdateUserInfoEvent;
import cn.sanshaoxingqiu.ssbm.module.splash.dialog.BenefitPolicyDialog;

/**
 * 启动页
 *
 * @Author yuexingxing
 * @time 2020/7/21
 */
public class LaunchActivity extends BaseActivity<BaseViewModel, ActivityLaunchBinding> implements IVerfyApkModel {
    private final String TAG = LaunchActivity.class.getSimpleName();
    private LoginViewModel mLoginViewModel;

    @Override
    public void initData() {
        mLoginViewModel = new LoginViewModel();
        mLoginViewModel.setIVerfyApkModel(this);
        mLoginViewModel.getPlatParamByParamKey("itomix", "android_review");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }

    private void check() {
        if (checkShowPolicy()) {
            countDownTimer.start();
        } else {
            new BenefitPolicyDialog().show(this, new CommonCallBack() {
                @Override
                public void callback(int postion, Object object) {
                    if (postion == 0) {
                        finish();
                    } else {
                        ACache.get(LaunchActivity.this).put(ConfigSP.SP_PERSONAL_POLICY, "");
                        countDownTimer.start();
                    }
                }
            });
        }
    }

    /**
     * 是否弹出隐私政策弹窗
     *
     * @return
     */
    private boolean checkShowPolicy() {
        if (ACache.get(this).getAsString(ConfigSP.SP_PERSONAL_POLICY) == null) {
            return false;
        }
        return true;
    }

    private CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            jump2Main();
        }
    };

    private void jump2Main() {
        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onIdentityExpiredEvent(IdentityExpiredEvent identityExpiredEvent) {
        SSApplication.setToken("");//身份过期后token置空
        LoginActivity.start(context);
        finish();
    }

    @Override
    public void onVerfyApk(VerifyApkInfo verifyApkInfo) {
        if (verifyApkInfo == null || TextUtils.isEmpty(verifyApkInfo.param_value)) {
            jump2Main();
            return;
        }

        String channelName = AppManager.getChannelName(LaunchActivity.this);
        String versionCode = verifyApkInfo.getVersionCodeByChannelName(channelName);
        String currentVersionCode = Kits.Package.getVersionName(LaunchActivity.this);

        Log.d(TAG, channelName + "/" + versionCode + "/" + currentVersionCode);
        if (TextUtils.isEmpty(versionCode) || TextUtils.isEmpty(currentVersionCode)) {
            check();
            return;
        }

        //应用市场版本比当前版本小，该版本还在审核
        if (versionCode.compareTo(currentVersionCode) < 0) {
            BasicApplication.app.isAPPVerfySuccess = false;
            Log.d(TAG, channelName + "-审核未通过-" + verifyApkInfo.param_value);
        } else {
            BasicApplication.app.isAPPVerfySuccess = true;
            Log.d(TAG, channelName + "-审核已通过-" + verifyApkInfo.param_value);
        }
        check();
    }
}