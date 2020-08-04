package com.sanshao.bs.module.order.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderListResponse implements Serializable {

    public int count;

    @SerializedName("rows")
    public List<OrderInfo> content;
}
