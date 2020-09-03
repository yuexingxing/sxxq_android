package cn.sanshaoxingqiu.ssbm.module.personal.myfans.model;

import java.util.List;

import cn.sanshaoxingqiu.ssbm.module.personal.bean.UserInfo;

public interface IFansCallBack {

    void showFans(List<UserInfo> fansList);

}
