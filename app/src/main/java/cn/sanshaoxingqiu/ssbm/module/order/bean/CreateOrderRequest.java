package cn.sanshaoxingqiu.ssbm.module.order.bean;

import java.util.List;

public class CreateOrderRequest {

    public String note;
    public List<CartInfo> cart;

    public static class CartInfo {
        public String sarti_id;
        public int qty;
    }
}
