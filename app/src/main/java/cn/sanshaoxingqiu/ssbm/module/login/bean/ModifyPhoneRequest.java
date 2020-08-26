package cn.sanshaoxingqiu.ssbm.module.login.bean;

public class ModifyPhoneRequest {

    public String new_phone;
    public String ver_code;

    public ModifyPhoneRequest() {

    }

    public ModifyPhoneRequest(String new_phone, String ver_code) {
        this.new_phone = new_phone;
        this.ver_code = ver_code;
    }
}
