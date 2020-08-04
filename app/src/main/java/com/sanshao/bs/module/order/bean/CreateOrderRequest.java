package com.sanshao.bs.module.order.bean;

import java.util.List;

public class CreateOrderRequest {

    public String corp_id;
    public String note;
    public List<CartInfo> cart;

    public static class CartInfo {
        public String sarti_id;
        public int qty;
    }
}
