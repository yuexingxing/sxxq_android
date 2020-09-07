package cn.sanshaoxingqiu.ssbm.module.personal.model;

import com.exam.commonbiz.bean.UserInfo;

/**
 * @Author yuexingxing
 * @time 2020/7/22
 */
public interface IPersonalCallBack {
    void returnUserInfo(UserInfo userInfo);

    void returnUpdateUserInfo(UserInfo userInfo);
}
