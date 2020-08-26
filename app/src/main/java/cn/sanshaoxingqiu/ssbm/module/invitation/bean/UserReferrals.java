package cn.sanshaoxingqiu.ssbm.module.invitation.bean;

import java.io.Serializable;
import java.util.List;

public class UserReferrals implements Serializable {

    public int referralsTotal;

    public List<UserReferralsItem> referrals;

    public static class UserReferralsItem  implements Serializable {

        public String avatar;

        public String nickname;

        public String mem_class_key;

        public String mem_class_name;

        public String mem_phone;
    }
}
