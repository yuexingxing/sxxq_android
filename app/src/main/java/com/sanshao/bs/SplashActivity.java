package com.sanshao.bs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.bs.databinding.ActivitySplashBinding;
import com.sanshao.bs.module.MainActivity;
import com.sanshao.bs.util.Constants;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 闪屏广告页
 *
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class SplashActivity extends BaseActivity<BaseViewModel, ActivitySplashBinding> {

    private int mJumpTime = 1;//跳过倒计时提示5秒
    private Timer mTimer = new Timer();

    public static void start(Context context) {
        Intent starter = new Intent(context, SplashActivity.class);
        context.startActivity(starter);
        ((Activity) context).finish();
    }

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

        mTimer.schedule(timerTask, 1000, 1000);
        Glide.with(SSApplication.app).load(Constants.DEFAULT_IMG_URL).into(binding.ivIcon);
        binding.tvTime.getBackground().setAlpha(79);
        binding.tvTime.setOnClickListener(view -> {
            jump();
        });
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    mJumpTime--;
                    binding.tvTime.setText(mJumpTime + "s 跳过");
                    if (mJumpTime < 1) {
                        jump();
                    }
                }
            });
        }
    };

    private void jump() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}