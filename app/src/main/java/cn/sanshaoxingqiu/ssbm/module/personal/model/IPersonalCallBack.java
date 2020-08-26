package cn.sanshaoxingqiu.ssbm.module.personal.model;

import cn.sanshaoxingqiu.ssbm.module.personal.bean.UserInfo;

/**
 * @Author yuexingxing
 * @time 2020/7/22
 */
public interface IPersonalCallBack {
    void returnUserInfo(UserInfo userInfo);

    void returnUpdateUserInfo(UserInfo userInfo);
}
