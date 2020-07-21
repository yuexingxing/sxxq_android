package com.sanshao.livemodule.zhibo.main.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.sanshao.livemodule.zhibo.login.TCLoginActivity;

/**
 *  Module:   TCSplashActivity
 *
 *  Function: 闪屏页面，只是显示一张图
 *
 *  Note：需要注意配置小直播后台的 server 地址；配置教程，详见：https://cloud.tencent.com/document/product/454/15187
 */
public class TCSplashActivity extends Activity {
    private static final String SP_NAME = "xiaozhibo_info";
    private static final String KEY_FIRST_RUN = "is_first_run";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (!isTaskRoot()
//                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
//                && getIntent().getAction() != null
//                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {
//
//            finish();
//            return;
//        }
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                jumpToLoginActivity();
            }
        }, 1000);
    }

    /**
     *  跳转到登录界面
     */
    private void jumpToLoginActivity() {
        Intent intent = new Intent(this, TCLoginActivity.class);
        startActivity(intent);
//        finish();
    }

}
