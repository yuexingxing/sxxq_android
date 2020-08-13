package com.sanshao.bs.module.login.bean;

public class LoginRequest {

    public String phone;
    public String ver_code;
    public String referrer_mem_id;

    public LoginRequest() {

    }

    public LoginRequest(String phone, String ver_code, String referrer_mem_id) {
        this.phone = phone;
        this.ver_code = ver_code;
        this.referrer_mem_id = referrer_mem_id;
    }
}
