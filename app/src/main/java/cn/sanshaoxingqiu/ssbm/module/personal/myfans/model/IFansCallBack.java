package cn.sanshaoxingqiu.ssbm.module.personal.myfans.model;

import cn.sanshaoxingqiu.ssbm.module.invitation.bean.UserReferrals;

import java.util.List;

public interface IFansCallBack {

    void showFans(List<UserReferrals.UserReferralsItem> fansList);

}
