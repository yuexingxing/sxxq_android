package com.sanshao.bs.module.invitation.model;

import com.sanshao.bs.module.invitation.bean.UserReferrals;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;

import java.util.List;

public interface InvitationCallBack {

    void showGoods(List<GoodsDetailInfo> goodsList);

    void showUserReferrals(UserReferrals userReferrals);
}
