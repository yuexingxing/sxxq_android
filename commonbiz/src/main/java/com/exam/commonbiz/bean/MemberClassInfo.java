package com.exam.commonbiz.bean;

import android.text.TextUtils;

import java.io.Serializable;

public class MemberClassInfo implements Serializable {
    public String mem_class_id;//会员等级配置id
    public String mem_class_key;//1，2，3对应一星，二星，三星会员
    public String mem_class_name;//会员等级名称
    public int mem_class_valid_days;//会员等级有效天数
    public String floor_amt;//实付xx元才能升到当前升级

    /**
     * 是不是星级会员
     * @return
     */
    public boolean isMember() {
        if (TextUtils.equals(mem_class_key, "1")) {
            return true;
        } else if (TextUtils.equals(mem_class_key, "2")) {
            return true;
        } else if (TextUtils.equals(mem_class_key, "3")) {
            return true;
        }
        return false;
    }
}
