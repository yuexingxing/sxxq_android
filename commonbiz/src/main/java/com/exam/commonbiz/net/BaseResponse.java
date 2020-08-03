package com.exam.commonbiz.net;

import android.text.TextUtils;
import android.util.Log;

/**
 * 网络返回基类 支持泛型
 *
 * @Author yuexingxing
 * @time 2020/6/11
 */
public class BaseResponse<T> {

    private String msg;
    private String ret;
    private T content;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public boolean isOk() {
        return TextUtils.equals("OK", ret);
    }

    @Override
    public String toString() {

        Log.v("zd", "BaseResponse{" +
                "code=" + ret +
                ", msg='" + msg + '\'' +
                ", data=" + content +
                '}');

        return "BaseResponse{" +
                "code=" + ret +
                ", msg='" + msg + '\'' +
                ", data=" + content +
                '}';
    }
}
