package com.sanshao.bs.module.splash;

import android.content.Intent;
import android.os.CountDownTimer;

import com.bumptech.glide.Glide;
import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.ActivitySplashBinding;
import com.sanshao.bs.module.MainActivity;
import com.sanshao.bs.util.Constants;

/**
 * 闪屏广告页
 *
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class SplashActivity extends BaseActivity<BaseViewModel, ActivitySplashBinding> implements ISplashCallBack {

    private long mJumpTime = 1000;//跳过倒计时提示5秒
    private SplashViewModel mSplashViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
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

        mSplashViewModel = new SplashViewModel();
//        mSplashViewModel.getSplashInfo("1", this);
        Glide.with(SSApplication.app).load(Constants.DEFAULT_IMG_URL).into(binding.ivIcon);
        binding.tvTime.getBackground().setAlpha(79);
        binding.tvTime.setOnClickListener(view -> {
            jump();
        });
        countDownTimer.start();
    }

    private CountDownTimer countDownTimer = new CountDownTimer(mJumpTime, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            binding.tvTime.setText((millisUntilFinished / 1000) + "s 跳过");
        }

        @Override
        public void onFinish() {
            jump();
        }
    };

    private void jump() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void returnSplashInfo(SplashInfo splashInfo) {
        if (splashInfo == null) {
            return;
        }
        Glide.with(SSApplication.app).load(splashInfo.url).into(binding.ivIcon);
    }
}