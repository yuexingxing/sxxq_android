package cn.sanshaoxingqiu.ssbm.module.invitation.bean;

import java.io.Serializable;
import java.util.List;

import com.exam.commonbiz.bean.UserInfo;

public class UserReferrals implements Serializable {

    public int referralsTotal;
    public String point;

    public List<UserInfo> referrals;
}
