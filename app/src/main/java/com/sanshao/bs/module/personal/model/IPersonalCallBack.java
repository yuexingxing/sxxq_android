package com.sanshao.bs.module.personal.model;

import com.sanshao.bs.module.personal.bean.UserInfo;

/**
 * @Author yuexingxing
 * @time 2020/7/22
 */
public interface IPersonalCallBack {
    void returnUserInfo(UserInfo userInfo);

    void returnUpdateUserInfo(UserInfo userInfo);
}
