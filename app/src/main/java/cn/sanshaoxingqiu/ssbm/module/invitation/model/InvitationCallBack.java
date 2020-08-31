package cn.sanshaoxingqiu.ssbm.module.invitation.model;

import cn.sanshaoxingqiu.ssbm.module.invitation.bean.UserReferrals;
import cn.sanshaoxingqiu.ssbm.module.personal.bean.UserInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;

import java.util.List;

public interface InvitationCallBack {

    void showGoods(List<GoodsDetailInfo> goodsList);

    void showUserReferrals(UserReferrals userReferrals);

    void showUserReferralsPoint(List<UserInfo> userInfoList);
}
