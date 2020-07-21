package com.sanshao.bs.util;

import android.text.TextUtils;

import java.util.UUID;

/**
 * @Author yuexingxing
 * @time 2020/6/29
 */
public class CommandTools {

    /**
     * 判断字符串是否符合手机号码格式
     *
     * @param phone
     * @return 待检测的字符串
     */
    public static boolean isMobileNum(String phone) {
        String telRegex = "^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$";
        if (TextUtils.isEmpty(phone))
            return false;
        else
            return phone.matches(telRegex);
    }

    public static String getUUID(){
        return String.valueOf(UUID.randomUUID());
    }

}
