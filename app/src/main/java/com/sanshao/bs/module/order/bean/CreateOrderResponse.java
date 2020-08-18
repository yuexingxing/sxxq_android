package com.sanshao.bs.module.order.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CreateOrderResponse implements Serializable {

    @SerializedName("salebill_id")
    public String orderNo;
    @SerializedName("sum_amt")
    public String orderPrice;
    @SerializedName("sum_point")
    public String sumPoint;
    public String sarti_name;
}
