package cn.sanshaoxingqiu.ssbm.module.personal.income.bean;

import java.io.Serializable;

public class IncomeBean implements Serializable {

    public String commission;//总佣金
    public String invitation_code;//邀请码
    public String mem_id;//用户主键
    public double underway;//提现中金额
    public double used_price;//剩余可提现金额
}
