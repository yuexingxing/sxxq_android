package com.sanshao.bs.module.login.bean;

import java.util.List;

/**
 * @Author yuexingxing
 * @time 2020/6/12
 */
public class WhetherBean {
    public String city;
    public String ganmao;
    public String wendu;
    public DataBean yesterday;
    public List<DataBean2> forecast;

    public class DataBean{
        public String date;
    }

    public class DataBean2{
        public String date;
    }
}
