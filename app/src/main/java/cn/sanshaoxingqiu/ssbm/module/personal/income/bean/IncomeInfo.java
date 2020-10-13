package cn.sanshaoxingqiu.ssbm.module.personal.income.bean;

import android.text.TextUtils;

import java.util.List;

public class IncomeInfo {

    public String income_date;
    public List<IncomeDetailInfo> income;

    public class IncomeDetailInfo {

        public String commission;
        public String commission_status;
        public String optr_date;
        public String salebill_id;
        public String sum_amt;

        public String getCommissionStatus() {
            if (TextUtils.equals("APPLY", commission_status)) {
                return "待进账";
            } else if (TextUtils.equals("ENABLE", commission_status)) {
                return "已进账";
            } else if (TextUtils.equals("DISABLE", commission_status)) {
                return "进账失效";
            } else {
                return "";
            }
        }
    }
}
