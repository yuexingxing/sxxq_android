package com.sanshao.bs.module.personal.myfans.model;

import com.sanshao.bs.module.invitation.bean.UserReferrals;

import java.util.List;

public interface IFansCallBack {

    void showFans(List<UserReferrals.UserReferralsItem> fansList);

}
