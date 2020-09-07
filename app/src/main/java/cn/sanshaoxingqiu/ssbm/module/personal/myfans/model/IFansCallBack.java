package cn.sanshaoxingqiu.ssbm.module.personal.myfans.model;

import java.util.List;

import com.exam.commonbiz.bean.UserInfo;

public interface IFansCallBack {

    void showFans(List<UserInfo> fansList);

}
