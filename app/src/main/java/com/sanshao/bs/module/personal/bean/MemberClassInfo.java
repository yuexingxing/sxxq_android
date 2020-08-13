package com.sanshao.bs.module.personal.bean;

import java.io.Serializable;

public class MemberClassInfo implements Serializable {
    public String mem_class_id;//会员等级配置id
    public String mem_class_key;
    public String mem_class_name;//会员等级名称
    public int mem_class_valid_days;//会员等级有效天数
    public String floor_amt;//实付xx元才能升到当前升级
}
