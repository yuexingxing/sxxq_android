package com.sanshao.bs.module.personal.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * @Author yuexingxing
 * @time 2020/7/2
 */
public class UserInfo implements Serializable {
    public String mem_id;
    public String gender;
    public String nickname;
    public String birthday;
    public String signature;
    public String avatar;
    public String invitation_code;
    public String invitation_weapp_url;
    public String mem_phone;
    public String mem_class_id;
    public String mem_class_start_date;
    public String mem_status;//
    public String point;//我的分享金
    public MemberClassInfo mem_class;
    public UserInfo referrer_mem;
    public String commission;//我的分润

    //是不是会员
    public boolean isMember() {
        if (mem_class == null || TextUtils.isEmpty(mem_class.mem_class_key)) {
            return false;
        }
        return true;
    }

    public String getMember() {
        if (mem_class == null || TextUtils.isEmpty(mem_class.mem_class_key)) {
            return "普通会员";
        } else if (TextUtils.equals("1", mem_class.mem_class_key)) {
            return "一星会员";
        } else if (TextUtils.equals("2", mem_class.mem_class_key)) {
            return "二星会员";
        } else {
            return "三星会员";
        }
    }

    public String getGender() {
        if (TextUtils.equals("M", gender)) {
            return "男";
        } else {
            return "女";
        }
    }
}
