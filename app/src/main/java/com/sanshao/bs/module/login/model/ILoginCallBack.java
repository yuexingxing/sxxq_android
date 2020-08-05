package com.sanshao.bs.module.login.model;

import com.sanshao.bs.module.login.bean.LoginResponse;

/**
 * @Author yuexingxing
 * @time 2020/6/30
 */
public interface ILoginCallBack {
    void onGetCode();

    void onLoginSuccess(LoginResponse loginResponse);

    void onLoginFailed();

    void onModifyPhone(String phone);
}
