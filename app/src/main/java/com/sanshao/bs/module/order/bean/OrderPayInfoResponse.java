package com.sanshao.bs.module.order.bean;

public class OrderPayInfoResponse {

    public String id;
    public String app_id;
    public String status;
    public OrderInfo expend;
    public String pay_channel;
    public String query_url;

    public class OrderInfo{
        public String pay_info;
    }
}
