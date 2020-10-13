package cn.sanshaoxingqiu.ssbm.module.personal.income.bean;

public class WithdrawInfo {

    public String withdraw_date;
    public WithdrawDetailnfo withdraw;

    public class WithdrawDetailnfo {
        public String arrive_amount;
        public String create_date;
        public String used_price;
        public String withdraw_amount;
        public String withdraw_status;
    }
}
