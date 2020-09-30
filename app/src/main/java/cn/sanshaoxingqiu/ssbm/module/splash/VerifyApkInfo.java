package cn.sanshaoxingqiu.ssbm.module.splash;

import android.text.TextUtils;

public class VerifyApkInfo {

    public String param_value;

    /**
     * a=官方
     * b=应用宝
     * c=华为
     * d=OPPO
     * e=VIVO
     * f=魅族
     * g=小米
     * h=360
     * i=百度
     *
     * @param channelName
     * @return "param_value": "a=1.0.0&b=1.0.0&c=1.0.0&d=1.0.0&e=1.0.0&f=1.0.0&g=1.0.0&h=1.0.0&i=1.0.0"
     */
    public String getVersionCodeByChannelName(String channelName) {
        String[] strChannelArray = param_value.split("&");
        String versionCode = "1.0";
        if (strChannelArray == null) {
            return versionCode;
        }
        for (int i = 0; i < strChannelArray.length; i++) {
            String[] strChanel = strChannelArray[i].split("=");
            if (TextUtils.equals("a", strChanel[0]) && TextUtils.equals("sanshao", channelName)) {
                versionCode = strChanel[1];
                break;
            }else if (TextUtils.equals("b", strChanel[0]) && TextUtils.equals("myapp", channelName)) {
                versionCode = strChanel[1];
                break;
            }else if (TextUtils.equals("c", strChanel[0]) && TextUtils.equals("huawei", channelName)) {
                versionCode = strChanel[1];
                break;
            }else if (TextUtils.equals("d", strChanel[0]) && TextUtils.equals("oppo", channelName)) {
                versionCode = strChanel[1];
                break;
            }else if (TextUtils.equals("e", strChanel[0]) && TextUtils.equals("vivo", channelName)) {
                versionCode = strChanel[1];
                break;
            }else if (TextUtils.equals("f", strChanel[0]) && TextUtils.equals("meizu", channelName)) {
                versionCode = strChanel[1];
                break;
            }else if (TextUtils.equals("g", strChanel[0]) && TextUtils.equals("xiaomi", channelName)) {
                versionCode = strChanel[1];
                break;
            }else if (TextUtils.equals("h", strChanel[0]) && TextUtils.equals("qh360", channelName)) {
                versionCode = strChanel[1];
                break;
            }else if (TextUtils.equals("i", strChanel[0]) && TextUtils.equals("baidu", channelName)) {
                versionCode = strChanel[1];
                break;
            }
        }
        return versionCode;
    }
}
