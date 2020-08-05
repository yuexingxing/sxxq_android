package com.sanshao.bs.module.personal.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

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
    public MemberClass mem_class;
    public String mem_phone;

    public String getGender() {
        if (TextUtils.equals("M", gender)) {
            return "男";
        } else {
            return "女";
        }
    }

    public class MemberClass {
        public String mem_class_id;
        public String mem_class_key;
        public String mem_class_name;//会员等级名称
        public String mem_class_valid_days;//会员等级有效天数
        public String floor_amt;//实付xx元才能升到当前升级
    }

    public class ReferMember {


    }
}
