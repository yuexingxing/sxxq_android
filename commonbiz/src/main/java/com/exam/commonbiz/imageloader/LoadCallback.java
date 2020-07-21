package com.exam.commonbiz.imageloader;

import android.graphics.Bitmap;

/**
 * @Author yuexingxing
 * @time 2020/6/10
 */
public abstract class LoadCallback {
    void onLoadFailed(Throwable e) {}

    public abstract void onLoadReady(Bitmap bitmap);

    void onLoadCanceled() {}
}
