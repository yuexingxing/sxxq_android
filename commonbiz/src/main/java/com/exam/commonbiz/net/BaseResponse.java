package com.exam.commonbiz.net;

import android.util.Log;

/**
 * 网络返回基类 支持泛型
 * @Author yuexingxing
 * @time 2020/6/11
 */
public class BaseResponse<T> {

    private String desc;
    private int status;
    private T data;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk() {
        return status == 0;
    }

    @Override
    public String toString() {

        Log.v("zd", "BaseResponse{" +
                "code=" + status +
                ", msg='" + desc + '\'' +
                ", data=" + data +
                '}');

        return "BaseResponse{" +
                "code=" + status +
                ", msg='" + desc + '\'' +
                ", data=" + data +
                '}';
    }
}
