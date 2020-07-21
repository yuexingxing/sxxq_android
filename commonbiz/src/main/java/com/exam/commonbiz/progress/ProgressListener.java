package com.exam.commonbiz.progress;

/**
 * @Author yuexingxing
 * @time 2020/6/10
 */
public interface ProgressListener {
    void onProgress(long soFarBytes, long totalBytes);

    void onError(Throwable throwable);
}
