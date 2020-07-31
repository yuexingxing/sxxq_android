package com.exam.commonbiz.base;

public interface IBaseModel {

    void onRefreshData(Object object);

    void onLoadMoreData(Object object);

    void onNetError();
}
