package com.exam.commonbiz.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * @Author yuexingxing
 * @time 2020/7/2
 */
public class UserInfo implements Serializable {
    public String userId;
    public String userSign;
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
    public int available_point;//可用分享金
    public int free_sarti_count;//免费商品领取次数
    public MemberClassInfo mem_class;
    public UserInfo referrer_mem;
    public String commission;//我的分润
    public String mem_class_name;
    public String point_status;
    public String frontcover;//直播封面

    //有没有分享金
    public boolean isZeroPoint() {
        return TextUtils.isEmpty(point) || TextUtils.equals("0", point);
    }

    //是不是会员
    public boolean isMember() {
        if (mem_class == null || TextUtils.isEmpty(mem_class.mem_class_key)) {
            return false;
        }
        return true;
    }

    public String getMember() {
        if (mem_class == null || TextUtils.isEmpty(mem_class.mem_class_key)) {
            return "普通用户";
        } else if (TextUtils.equals("1", mem_class.mem_class_key)) {
            return "一星粉丝";
        } else if (TextUtils.equals("2", mem_class.mem_class_key)) {
            return "二星粉丝";
        } else {
            return "三星粉丝";
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
