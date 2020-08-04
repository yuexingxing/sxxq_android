package com.exam.commonbiz.net.interceptor;

import com.exam.commonbiz.base.BasicApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author yuexingxing
 * @time 2020/6/10
 */
public class HeadInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        String versionName = getVersionName();
        String platform = getPlatform();

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Content-type", "application/json; charset=utf-8");
        builder.addHeader("Accept", "application/json");
        builder.addHeader("User-Agent", "Client/Android V1.0");
        builder.addHeader("wx-token", BasicApplication.getToken());
        builder.addHeader("we-app", "ssxq-android");
        builder.addHeader("we-plat-id", "SSBM");

//        we-app: ssxq-mini
//        we-plat-id: SSBM
//        wx-token: 7ee2c7ffc52b4bf786671ffe2e8f72bb
//        builder.addHeader("User-Agent", "ZMLearn-AM");
//        builder.addHeader("Api-Version", "2.0.0");
//        builder.addHeader("App-Version", versionName);
//        builder.addHeader("Platform", platform);
//        builder.addHeader("Channel-Name", channelName);
//        if (!StringUtils.isEmpty(BasicApplication.getSessonId())) {
//            builder.header("Cookie", "sessionid=" + BasicApplication.getSessonId());
//        } else {
//            builder.removeHeader("Cookie");
//        }
        Request newRequest = builder.build();
        Response response = chain.proceed(newRequest);
        return response;
    }

    //am表示安卓手机,apad表示安卓平板
    private String getPlatform() {
        String platform = "am";
        return platform;
    }


    //获取版本名
    private String getVersionName() {
        String versionName = "";
//        Context context = BaseApplication.getInstance();
//        try {
//            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        return versionName;
    }
}
