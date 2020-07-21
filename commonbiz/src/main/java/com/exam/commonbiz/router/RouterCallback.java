package com.exam.commonbiz.router;

import android.app.Activity;

/**
 * @Author yuexingxing
 * @time 2020/6/10
 */
public interface RouterCallback {

    void onBefore(Activity from, Class<?> to);

    void onNext(Activity from, Class<?> to);

    void onError(Activity from, Class<?> to, Throwable throwable);

}
