package com.exam.commonbiz.base;

public interface IBaseModel extends BaseViewCallBack{

    void onRefreshData(Object object);

    void onLoadMoreData(Object object);

    void onNetError();
}
