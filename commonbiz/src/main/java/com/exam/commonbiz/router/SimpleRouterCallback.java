package com.exam.commonbiz.router;

import android.app.Activity;

import com.exam.commonbiz.router.RouterCallback;

/**
 * @Author yuexingxing
 * @time 2020/6/10
 */
public class SimpleRouterCallback implements RouterCallback {

    @Override
    public void onBefore(Activity from, Class<?> to) {

    }

    @Override
    public void onNext(Activity from, Class<?> to) {

    }

    @Override
    public void onError(Activity from, Class<?> to, Throwable throwable) {

    }
}
