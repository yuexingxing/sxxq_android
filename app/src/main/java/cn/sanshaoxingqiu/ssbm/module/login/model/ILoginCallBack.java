package cn.sanshaoxingqiu.ssbm.module.login.model;

import cn.sanshaoxingqiu.ssbm.module.login.bean.LoginResponse;

import com.exam.commonbiz.bean.UserInfo;

/**
 * @Author yuexingxing
 * @time 2020/6/30
 */
public interface ILoginCallBack {
    void onGetCode();

    void onLoginSuccess(LoginResponse loginResponse);

    void onLoginFailed();

    void onModifyPhone(String phone);

    void onMemInfoByInvitationCode(UserInfo userInfo);
}
