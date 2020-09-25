package cn.sanshaoxingqiu.ssbm;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.multidex.BuildConfig;
import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.exam.commonbiz.base.BasicApplication;
import com.exam.commonbiz.cache.ACache;
import com.exam.commonbiz.config.ConfigSP;
import com.exam.commonbiz.net.HostUrl;
import com.exam.commonbiz.net.NetError;
import com.exam.commonbiz.net.NetProvider;
import com.exam.commonbiz.net.RequestHandler;
import com.exam.commonbiz.net.XApi;
import com.exam.commonbiz.util.Constants;
import com.sanshao.commonui.titlebar.TitleBar;
import com.sanshao.commonui.titlebar.TitleBarLightStyle;
import com.sanshao.livemodule.zhibo.TCGlobalConfig;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import cn.sanshaoxingqiu.ssbm.util.AppUtil;
import cn.udesk.UdeskSDKManager;

/**
 * @Author yuexingxing
 * @time 2020/6/10
 */
public class SSApplication extends BasicApplication {

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

        initUMeng();
        closeAndroidPDialog();

        //bugly日志统计
        CrashReport.initCrashReport(getApplicationContext(), "ea6de64e88", false);

//        if (AppUtil.isDebug(app)) {
//            List<IKit> kits = new ArrayList<>();
//            kits.add(new KitChangeHost());
//            DoraemonKit.install(this, kits);
//        } else {
//
//        }

//        PlatformConfig.setWeixin("微信AppId", "微信AppSecret");

        TCGlobalConfig.init(this);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        if (!LeakCanary.isInAnalyzerProcess(this)) {
//            LeakCanary.install(this);
        }

        UdeskSDKManager.getInstance().initApiKey(getApplicationContext(), "sanshaoxingqiu.s2.udesk.cn", "e10d38a74025f86a3240885761146d18", "49ac88a587043728");
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

        Map<String, String> hostMap = new HashMap<>();
        if (AppUtil.isDebug(this)) {
            if (ACache.get(this).getAsObject(ConfigSP.SP_CURRENT_HOST) != null) {
                ConfigSP.HOST_TYPE hostType = (ConfigSP.HOST_TYPE) ACache.get(this).getAsObject(ConfigSP.SP_CURRENT_HOST);
                if (ConfigSP.HOST_TYPE.DEV == hostType) {
                    hostMap.put(XApi.HOST_TYPE.JAVA, HostUrl.DEV.JAVA);
                    hostMap.put(XApi.HOST_TYPE.NODE, HostUrl.DEV.NODE);
                } else if (ConfigSP.HOST_TYPE.PRE == hostType) {
                    hostMap.put(XApi.HOST_TYPE.JAVA, HostUrl.PRE.JAVA);
                    hostMap.put(XApi.HOST_TYPE.NODE, HostUrl.PRE.NODE);
                } else {
                    hostMap.put(XApi.HOST_TYPE.JAVA, HostUrl.PRO.JAVA);
                    hostMap.put(XApi.HOST_TYPE.NODE, HostUrl.PRO.NODE);
                }
            } else {
                hostMap.put(XApi.HOST_TYPE.JAVA, HostUrl.DEV.JAVA);
                hostMap.put(XApi.HOST_TYPE.NODE, HostUrl.DEV.NODE);
                ACache.get(this).put(ConfigSP.SP_CURRENT_HOST, ConfigSP.HOST_TYPE.DEV);
            }
        } else {
            hostMap.put(XApi.HOST_TYPE.JAVA, HostUrl.PRO.JAVA);
            hostMap.put(XApi.HOST_TYPE.NODE, HostUrl.PRO.NODE);
        }

        XApi.setHostMap(hostMap);
        XApi.registerDefaultProvider(HostUrl.PRO.JAVA, new NetProvider() {

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
    }

    /**
     * 友盟相关初始化
     */
    public void initUMeng() {

        /**
         * 初始化common库
         * 参数1:上下文，必须的参数，不能为空
         * 参数2:友盟 app key，非必须参数，如果Manifest文件中已配置app key，该参数可以传空，则使用Manifest中配置的app key，否则该参数必须传入
         * 参数3:友盟 channel，非必须参数，如果Manifest文件中已配置channel，该参数可以传空，则使用Manifest中配置的channel，否则该参数必须传入，channel命名请详见channel渠道命名规范
         * 参数4:设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机
         * 参数5:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空
         */
        UMConfigure.init(this, Constants.UMENG_APP_KEY, "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        /**
         * 友盟相关平台配置。注意友盟官方新文档中没有这项配置，但是如果不配置会吊不起来相关平台的授权界面
         */
        PlatformConfig.setWeixin(Constants.WX_APPID, Constants.WX_APPSECRET);//微信APPID和AppSecret
//        PlatformConfig.setQQZone("你的QQAPPID", "你的QQAppSecret");//QQAPPID和AppSecret
//        PlatformConfig.setSinaWeibo("你的微博APPID", "你的微博APPSecret","微博的后台配置回调地址");//微博
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

    /**
     * 当前是否是正式环境
     *
     * @return
     */
    public boolean isProEnvironment() {
        ConfigSP.HOST_TYPE mCurrentIndex = (ConfigSP.HOST_TYPE) ACache.get(SSApplication.getInstance()).getAsObject(ConfigSP.SP_CURRENT_HOST);
        if (mCurrentIndex == null || ConfigSP.HOST_TYPE.PRO == mCurrentIndex) {
            return true;
        }
        return false;
    }
}
