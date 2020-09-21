package com.exam.commonbiz.util;

import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;

public class MyHandlerCallBack extends DefaultHandler {

    @Override
    public void handler(String data, CallBackFunction function) {
        if (function != null) {
            ToastUtil.showShortToast(data);
        }
    }
}
