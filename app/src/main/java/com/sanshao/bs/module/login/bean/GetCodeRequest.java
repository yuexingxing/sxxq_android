package com.sanshao.bs.module.login.bean;

public class GetCodeRequest {

    public String phone;
    public String pin_type;

    public GetCodeRequest(){

    }

    public GetCodeRequest(String phone, String pinType){
        this.phone = phone;
        this.pin_type = pinType;
    }
}
