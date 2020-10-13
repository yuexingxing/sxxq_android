package cn.sanshaoxingqiu.ssbm.module.personal.income.bean;

import android.text.TextUtils;

import java.util.List;

public class WithdrawInfo {

    public String withdraw_date;
    public List<WithdrawDetailnfo> withdraw;

    public class WithdrawDetailnfo {
        public String arrive_amount;
        public String create_date;
        public String used_price;
        public String withdraw_amount;
        public String withdraw_status;//APPLY=已申请提现，ENABLE=已同意提现申请，DISABLE=已拒绝提现申请，FINISH=已打款，FAIL=打款失败

        public String getWithdrawStatus() {
            if (TextUtils.equals("APPLY", withdraw_status)) {
                return "已申请提现";
            } else if (TextUtils.equals("ENABLE", withdraw_status)) {
                return "已同意提现申请";
            } else if (TextUtils.equals("DISABLE", withdraw_status)) {
                return "已拒绝提现申请";
            } else if (TextUtils.equals("FINISH", withdraw_status)) {
                return "已打款";
            } else if (TextUtils.equals("FAIL", withdraw_status)) {
                return "打款失败";
            } else {
                return "";
            }
        }
    }
}
