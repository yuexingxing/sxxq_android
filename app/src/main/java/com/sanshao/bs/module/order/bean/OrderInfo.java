package com.sanshao.bs.module.order.bean;

/**
 * @Author yuexingxing
 * @time 2020/7/1
 */
public class OrderInfo {
    public static final String ORDER_STATE = "order_state";
    public String name;
    public String price;
    public int state;
    public int buyNum;
    public interface State {
        int ALL = 0;
        int ToBePaid = 1;
        int ToBeUse = 2;
        int Complete = 3;
    }
}
