package com.sanshao.bs.module.login.bean;

public class LoginRequest {

    public String phone;
    public String ver_code;

    public LoginRequest() {

    }

    public LoginRequest(String phone, String ver_code) {
        this.phone = phone;
        this.ver_code = ver_code;
    }
}
