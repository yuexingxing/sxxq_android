package cn.sanshaoxingqiu.ssbm.module.personal.income.view.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeInfo;
import cn.sanshaoxingqiu.ssbm.util.MathUtil;

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
        helper.setText(R.id.tv_order_fee, "订单总额：¥" + MathUtil.getNumExclude0(item.sum_amt));
        helper.setText(R.id.tv_fee, "¥" + MathUtil.getNumExclude0(item.commission));
        helper.setText(R.id.tv_status, item.getCommissionStatus());

        if (!TextUtils.isEmpty(item.optr_date) && item.optr_date.length() > 16) {
            helper.setText(R.id.tv_time, "核销时间：" + item.optr_date.substring(5, 16));
        } else {
            helper.setText(R.id.tv_time, "核销时间：" + item.optr_date);
        }
    }
}
