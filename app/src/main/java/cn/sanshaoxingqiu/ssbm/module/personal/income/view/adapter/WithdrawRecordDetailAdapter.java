package cn.sanshaoxingqiu.ssbm.module.personal.income.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.WithdrawInfo;

/**
 * @Author yuexingxing
 * @time 2020/10/12
 */
public class WithdrawRecordDetailAdapter extends BaseQuickAdapter<WithdrawInfo.WithdrawDetailnfo, BaseViewHolder> {

    public WithdrawRecordDetailAdapter() {
        super(R.layout.item_layout_income_record_detail, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawInfo.WithdrawDetailnfo item) {
        helper.setText(R.id.tv_order_no, "可提现金额：" + item.used_price);
        helper.setText(R.id.tv_order_fee, "待入账金额：" + item.arrive_amount);
        helper.setText(R.id.tv_time, "时间：" + item.create_date);
        helper.setText(R.id.tv_fee, "¥" + item.withdraw_amount);
        helper.setText(R.id.tv_status, item.getWithdrawStatus());

    }
}
