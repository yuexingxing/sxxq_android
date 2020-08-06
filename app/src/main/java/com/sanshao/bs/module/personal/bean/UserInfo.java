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
    public MemberClassInfo mem_class;
    public ReferMemberInfo referrer_mem;
    public String mem_phone;


    public String getGender() {
        if (TextUtils.equals("M", gender)) {
            return "男";
        } else {
            return "女";
        }
    }

    public class ReferMemberInfo {


    }
}
