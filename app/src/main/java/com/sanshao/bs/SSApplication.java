package com.sanshao.bs;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.ArrayMap;
import android.util.Log;

import androidx.multidex.BuildConfig;
import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.kit.IKit;
import com.exam.commonbiz.base.BasicApplication;
import com.exam.commonbiz.cache.ACache;
import com.exam.commonbiz.config.ConfigSP;
import com.exam.commonbiz.kits.Kits;
import com.exam.commonbiz.net.NetError;
import com.exam.commonbiz.net.NetProvider;
import com.exam.commonbiz.net.RequestHandler;
import com.exam.commonbiz.net.XApi;
import com.sanshao.bs.module.personal.bean.UserInfo;
import com.sanshao.bs.util.AppUtil;
import com.sanshao.bs.module.personal.setting.dokit.KitChangeHost;
import com.sanshao.commonui.titlebar.TitleBar;
import com.sanshao.commonui.titlebar.TitleBarLightStyle;
import com.sanshao.livemodule.zhibo.TCGlobalConfig;
import com.sanshao.livemodule.zhibo.login.TCUserMgr;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;


/**
 * @Author yuexingxing
 * @time 2020/6/10
 */
public class SSApplication extends BasicApplication {

    public static String BASE_URL;
    public static SSApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        TitleBar.initStyle(new TitleBarLightStyle(this) {

            @Override
            public Drawable getBackIcon() {
                return getDrawable(R.drawable.icon_back);
            }

        });
        initHttpConfig();
        initRouter(this);

        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = true;
        initUMeng();
        closeAndroidPDialog();
        TCGlobalConfig.init(this);

        List<IKit> kits = new ArrayList<>();
        kits.add(new KitChangeHost());

        DoraemonKit.install(this, kits);
        // 必须：初始化全局的 用户信息管理类，记录个人信息。
        TCUserMgr.getInstance().initContext(getApplicationContext());
        PlatformConfig.setWeixin("微信AppId", "微信AppSecret");

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public static SSApplication getInstance() {
        if (mInstance == null) {
            mInstance = new SSApplication();
        }
        return mInstance;
    }

    public void initHttpConfig() {
        BASE_URL = Kits.Package.getMetaValue(this, "HTTP_HOST");
        BASE_URL = "http://dev.kmlab.com/ssxq/";
        //debug模式下允许切换服务器，这个设置一定要放到initHttpConfig初始化后面
        if (AppUtil.isDebug(this)) {
//            ACache.get(this).put(ConfigSP.SP_CURRENT_HOST, ConfigSP.HOST_TYPE.DEV);
        }

//        ACache.get(this).put(ConfigSP.SP_CURRENT_HOST, ConfigSP.HOST_TYPE.PRO);//默认线上服务器
        Map<String, String> hostMap = new HashMap<>();
        hostMap.put(XApi.HOST_TYPE.JAVA, XApi.HOST_URL.JAVA);
        hostMap.put(XApi.HOST_TYPE.NODE, XApi.HOST_URL.NODE);
        XApi.setHostMap(hostMap);

        XApi.registerDefaultProvider(BASE_URL, new NetProvider() {

            @Override
            public okhttp3.Interceptor[] configInterceptors() {
                return new okhttp3.Interceptor[0];
            }

            @Override
            public okhttp3.Interceptor[] configNetInterceptors() {
                return new okhttp3.Interceptor[0];
            }

            @Override
            public okhttp3.CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return null;
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                return false;
            }

            @Override
            public boolean handleError(NetError error) {
                return false;
            }

            @Override
            public boolean dispatchProgressEnable() {
                return false;
            }

            @Override
            public void configHttps(okhttp3.OkHttpClient.Builder builder) {

            }
        });
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                //异常处理
                Log.d("zdddz", "exception:" + throwable.getMessage());
            }
        });
    }

    /**
     * 友盟相关初始化
     */
    public void initUMeng() {

        /**
         * 参数1:上下文，不能为空
         * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数3:Push推送业务的secret
         */
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        UMShareAPI.get(this);//初始化sdk
    }

    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRouter(Application application) {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application);
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
}
