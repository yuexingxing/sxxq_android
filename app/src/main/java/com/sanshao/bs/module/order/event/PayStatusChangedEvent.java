package com.sanshao.bs.module.order.event;

/**
 * 支付状态改变通知
 *
 * @Author yuexingxing
 * @time 2020/7/14
 */
public class PayStatusChangedEvent {

    public static final int PAY_FAILED = 0;
    public static final int PAY_SUCCESS = 1;

    public boolean paySuccess;
}
