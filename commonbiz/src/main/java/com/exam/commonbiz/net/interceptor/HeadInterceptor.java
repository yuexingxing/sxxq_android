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
        builder.addHeader("User-Agent", "BookEdu Client/Android V1.0");
        String token = BasicApplication.getToken();
        builder.addHeader("Authorization", "Bearer " + BasicApplication.getToken());
//        builder.addHeader("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImVhM2RjODRlOTE3YzBlZmU4NGJmZmM4Yzk3ZmE5MjBjMTEzYTc2MTg4Y2M3YmMxYWI0ZjdlY2ZhYzYwZDY5MTkwMTA0ZTI0ODBlYTlkMmNiIn0.eyJhdWQiOiIxIiwianRpIjoiZWEzZGM4NGU5MTdjMGVmZTg0YmZmYzhjOTdmYTkyMGMxMTNhNzYxODhjYzdiYzFhYjRmN2VjZmFjNjBkNjkxOTAxMDRlMjQ4MGVhOWQyY2IiLCJpYXQiOjE1Mjg5NDY3MDIsIm5iZiI6MTUyODk0NjcwMiwiZXhwIjoxNTMwMjQyNzAyLCJzdWIiOiIxNiIsInNjb3BlcyI6W119.bPR1nd21fGZ_Rzip48vp0iL9PiUilij-mR4qNiB5mQpYPdDMP4PNDZonZIOTC8dgxOGrM4ebv6q0asFlPYn06bNdNXhf5YuF2RwAFz1vLKbwbUD5VTOg_Fd-sk6b8CboqUtNWN_EGDb03FAKDSzzjhwyQUwkm6zenCynmTBrTbzjKXbYyE90ULxEyfKYhcFKUt98YZazjFBbTGZ1ZiND_MigrRnZKHkNJyvC_mVl2eXIRP5rUqF8UeatN9PlFLKXewJM6wMwmEQ298gJeidOSySXAtGbiitIKaR_AwIPPM0dMyV9b3IQTMhwFEOovorwJMlFUZXz0I0DZp4zRbx5h4hYqQthgOj4vko_M3umJmgZJuc5PfzM34b-MmO_jPQQRZ2tU82SGOLuZuvrUp1R1k9qfsFYbpnIgwix6elT7PW98FAAFXMfie3RCym3CKWbxcQU18sTcOxVSKjqLXLMK_wKPFBctFDcmTsP9iOgktSBig7NOS242JR3RHWnysHmwkFfWK0JXWw3x4MJq_vuWJxVj5IhQE2AbHcZV81SKyMFPsmXfq47k1iFuLkOq7eokYce7b3GHJMQQa1tf5uW5R0FxyLCPBD7Sex9tIrq0N_2ZGhzHYoxbR6uZZTcaiAmA1P84y0m2XP2ECaIKEY_CmYWVliGT889IX16ECdb85w");
        builder.addHeader("X-App-Plantform", "Android");
        builder.addHeader("X-App-Version", "1.0.0");
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
