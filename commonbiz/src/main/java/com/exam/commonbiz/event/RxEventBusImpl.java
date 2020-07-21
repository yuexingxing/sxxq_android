package com.exam.commonbiz.event;


import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public class RxEventBusImpl implements IEventBus {

    private FlowableProcessor<Object> bus = null;

    private RxEventBusImpl() {
        bus = PublishProcessor.create().toSerialized();
    }

    public static RxEventBusImpl get() {
        return Holder.instance;
    }

    @Override
    public void register(Object object) {

    }

    @Override
    public void unregister(Object object) {

    }

    @Override
    public void post(IEvent event) {
        bus.onNext(event);
    }

    @Override
    public void postSticky(IEvent event) {

    }

    public <T extends IEvent> Flowable<T> toFlowable(Class<T> eventType) {
        return bus.ofType(eventType).onBackpressureBuffer();
    }

    private static class Holder {
        private static final RxEventBusImpl instance = new RxEventBusImpl();
    }
}
