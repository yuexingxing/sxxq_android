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
    public MemberClassInfo mem_class;
    public RefferMemberInfo referrer_mem;

    public class RefferMemberInfo{

    }

    public String getGender() {
        if (TextUtils.equals("M", gender)) {
            return "男";
        } else {
            return "女";
        }
    }
}
