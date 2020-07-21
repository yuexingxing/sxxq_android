package com.exam.commonbiz.net;

/**
 *
 * @Author yuexingxing
 * @time 2020/6/11
 */
public interface OnLoadListener<T> {

    void onLoadStart();

    void onLoadCompleted();

    void onLoadSucessed(BaseResponse<T> t);

    void onLoadFailed(String errMsg);
}
