package com.sanshao.bs.module.personal.bean;

import java.io.Serializable;

/**
 * @Author yuexingxing
 * @time 2020/7/2
 */
public class UserInfo implements Serializable {
    public int sex;
    public String sexName = "请选择";
    public String name;
    public String nickName = "sanshao";
    public String birthday;
    public String signature;
}
