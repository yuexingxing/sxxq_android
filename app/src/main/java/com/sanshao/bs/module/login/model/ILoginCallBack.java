package com.sanshao.bs.module.login.model;

/**
 * @Author yuexingxing
 * @time 2020/6/30
 */
public interface ILoginCallBack {
    void onGetCode();

    void onLoginSuccess();

    void onLoginFailed();
}
