package com.sanshao.bs.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.exam.commonbiz.cache.ACache;
import com.exam.commonbiz.config.ConfigSP;
import com.sanshao.bs.SSApplication;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public class AppUtil {

    /**
     * 更换服务器地址
     * 1-阿里
     * 2-线上
     */
    public static void resetHostUrl(int which) {

        if (which == 1) {
            SSApplication.BASE_URL = Constants.HOST_URL_ALI;
        } else {
            SSApplication.BASE_URL = Constants.HOST_URL_PRO;
        }

        Constants.HOST_URL = SSApplication.BASE_URL;
        ACache.get(SSApplication.app).put(ConfigSP.SP_CURRENT_HOST, which);
    }

    /**
     * 判断是debug还是release模式
     * 注意gradle中是否已经配置，正常情况不需要配置
     */
    public static boolean isDebug(Context context) {
        boolean isDebug = context.getApplicationInfo() != null &&
                (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        return isDebug;
    }
}
