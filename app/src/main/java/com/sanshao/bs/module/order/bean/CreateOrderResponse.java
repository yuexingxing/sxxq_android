package com.sanshao.bs.module.order.bean;

import com.google.gson.annotations.SerializedName;

public class CreateOrderResponse {

    @SerializedName("order_no")
    public String orderNo;
    @SerializedName("order_price")
    public String orderPrice;
    @SerializedName("order_point")
    public String orderPoint;
}
