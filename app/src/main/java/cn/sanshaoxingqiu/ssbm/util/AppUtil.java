package cn.sanshaoxingqiu.ssbm.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public class AppUtil {

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
