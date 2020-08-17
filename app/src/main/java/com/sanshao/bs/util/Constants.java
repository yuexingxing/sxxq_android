package com.sanshao.bs.util;

import com.sanshao.bs.SSApplication;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public class Constants {
    public static String HOST_URL = SSApplication.BASE_URL;

    public static final String HOST_URL_DEV = "http://api.book-developer.com/";
    public static final String HOST_URL_ALI = "https://v5.wrox.cn/";//阿里云
    public static final String HOST_URL_PRE = "http://pre-bukeapi.gaodunwangxiao.com/";
    public static final String HOST_URL_PRO = "http://wthrcdn.etouch.cn/";//http://bukeapi.gaodunwangxiao.com/
    public static final int REQUEST_COMPLETED = 100;
    public static final int REQUEST_COMPLETED_OK = 101;
    public static final int REQUEST_COMPLETED_WRONG = 102;
    public static final int NETWORK_STATUS = 1000;
    public static final int NETWORK_STATUS_CONNECTED = 10001;
    public static final int NETWORK_STATUS_NOCONNECTED = 10002;
    public static final int SERVER_STATUS = 2000;
    public static final int SERVER_STATUS_OK = 20001;
    public static final int SERVER_STATUS_WRONG = 20002;

    public static final int PAGE_SIZE = 10;
    public static final String OPT_TYPE = "opt_type";
    public static final String OPT_DATA = "opt_data";
    public static final String WX_APPId = "wx0cb916b177b454f4";

    public static final String TAG_ID_REGISTER  = "9f913fab33e04ecc9031413da1e7b3bf";
    public static final String TAG_ID_INVITE  = "b77ddad1b3834c7c9751f73ca9c5efee";

    public static final String SERVICE_PHONE = "4007163188";
    public static final String DEFAULT_IMG_BG = "http://jzvd-pic.nathen.cn/jzvd-pic/00b026e7-b830-4994-bc87-38f4033806a6.jpg";
    public static final String DEFAULT_IMG_URL = "http://img.cyw.com/shopx/20130606155913125664/shopinfo/201605041441522.jpg";
    public static final String VIDEO_PLAY_URL = "http://vfx.mtime.cn/Video/2019/06/29/mp4/190629004821240734.mp4";
}
