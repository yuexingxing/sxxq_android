package com.exam.commonbiz.event;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public class EventBusProvider {

    private static RxEventBusImpl bus;

    public static RxEventBusImpl getBus() {
        if (bus == null) {
            synchronized (EventBusProvider.class) {
                if (bus == null) {
                    bus = RxEventBusImpl.get();
                }
            }
        }
        return bus;
    }
}
