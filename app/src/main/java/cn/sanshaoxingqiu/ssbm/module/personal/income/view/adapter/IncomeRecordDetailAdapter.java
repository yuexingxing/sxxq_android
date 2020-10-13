package cn.sanshaoxingqiu.ssbm.module.personal.income.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeInfo;

/**
 * @Author yuexingxing
 * @time 2020/10/12
 */
public class IncomeRecordDetailAdapter extends BaseQuickAdapter<IncomeInfo.IncomeDetailInfo, BaseViewHolder> {

    public IncomeRecordDetailAdapter() {
        super(R.layout.item_layout_income_record_detail, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, IncomeInfo.IncomeDetailInfo item) {
        helper.setText(R.id.tv_order_no, "订单编号：" + item.salebill_id);
        helper.setText(R.id.tv_order_fee, "订单总额：" + item.sum_amt);
        helper.setText(R.id.tv_time, "核销时间：" + item.optr_date);
        helper.setText(R.id.tv_fee, "¥" + item.commission);
        helper.setText(R.id.tv_status, item.getCommissionStatus());

    }
}
