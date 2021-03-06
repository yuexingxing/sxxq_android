package com.exam.commonbiz.config;

/**
 * @Author yuexingxing
 * @time 2020/6/10
 */
public class ConfigSP {

    public static final String SP_LOGIN_NAME = "sp_login_name";//登录账号
    public static final String SP_LOGIN_PSD = "sp_login_psd";//登录密码
    public static final String SP_TOKEN = "sp_token";//token
    public static final String SP_USER_INFO = "sp_user_info";
    public static final String SP_CURRENT_HOST = "sp_current_host";
    public static final String SP_PERSONAL_POLICY = "sp_personal_policy";//用户权益弹窗

    public enum HOST_TYPE {
        PRO,
        DEV,
        PRE
    }
}
