package com.exam.commonbiz.event;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public interface IEventBus {

    void register(Object object);
    void unregister(Object object);
    void post(IEvent event);
    void postSticky(IEvent event);

    interface IEvent {
        int getTag();
    }

}
