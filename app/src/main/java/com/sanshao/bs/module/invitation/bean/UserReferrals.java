package com.sanshao.bs.module.invitation.bean;

import java.util.List;

public class UserReferrals {

    public int referralsTotal;

    public List<UserReferralsItem> referrals;

    public static class UserReferralsItem {

        public String avatar;

        public String nickname;
    }
}
