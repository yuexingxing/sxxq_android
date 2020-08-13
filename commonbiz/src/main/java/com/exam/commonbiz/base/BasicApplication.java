package com.exam.commonbiz.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.exam.commonbiz.cache.ACache;
import com.exam.commonbiz.config.ConfigSP;

/**
 * @Author yuexingxing
 * @time 2020/6/10
 */
public abstract class BasicApplication extends Application {
    public static BasicApplication app;
    protected Class restartClass;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 重启APP ，当遇到崩溃，或者其他导致APP发生崩溃的异常时， 会重启APP
     */
    public void restartApp() {
        if (restartClass != null) {
            Intent intent = new Intent(this, restartClass);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
        }
    }

    public static String getToken() {
        if (ACache.get(app).getAsString(ConfigSP.SP_TOKEN) == null) {
            return "";
        }
        return ACache.get(app).getAsString(ConfigSP.SP_TOKEN);
    }

    public static void setToken(String token) {
        ACache.get(app).put(ConfigSP.SP_TOKEN, token);
    }

    public static boolean isLogin() {
        return !TextUtils.isEmpty(getToken());
    }
}
