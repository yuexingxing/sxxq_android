package com.exam.commonbiz.base;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.exam.commonbiz.bean.UserInfo;
import com.exam.commonbiz.cache.ACache;
import com.exam.commonbiz.config.ConfigSP;

/**
 * @Author yuexingxing
 * @time 2020/6/10
 */
public abstract class BasicApplication extends Application {
    public static BasicApplication app;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public UserInfo getUserInfo() {
        if (ACache.get(app).getAsObject(ConfigSP.SP_USER_INFO) == null) {
            ACache.get(app).put(ConfigSP.SP_USER_INFO, new UserInfo());
        }
        return (UserInfo) ACache.get(app).getAsObject(ConfigSP.SP_USER_INFO);
    }

    public void saveUserInfo(UserInfo userInfo) {
        ACache.get(app).put(ConfigSP.SP_USER_INFO, userInfo);
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
