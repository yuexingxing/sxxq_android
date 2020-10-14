package cn.sanshaoxingqiu.ssbm.module.personal.income.view.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.dialog.CommonTipDialog;
import com.exam.commonbiz.util.Res;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.WithdrawInfo;
import cn.sanshaoxingqiu.ssbm.util.MathUtil;

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
        helper.setText(R.id.tv_order_no, "可提现金额：" + MathUtil.getNumExclude0(item.used_price) + "元");
        helper.setText(R.id.tv_order_fee, "待入账金额：" + MathUtil.getNumExclude0(item.arrive_amount) + "元");
        helper.setText(R.id.tv_status, item.getWithdrawStatus());

        if (!TextUtils.isEmpty(item.create_date) && item.create_date.length() > 16) {
            helper.setText(R.id.tv_time, "时间：" + item.create_date.substring(5, 16));
        } else {
            helper.setText(R.id.tv_time, "时间：" + item.create_date);
        }

        TextView tvFee = helper.getView(R.id.tv_fee);
        TextView tvStatus = helper.getView(R.id.tv_status);
        tvFee.setText("+" + MathUtil.getNumExclude0(item.withdraw_amount));
        if (TextUtils.equals("APPLY", item.withdraw_status)) {
            tvStatus.setText("提现中");
            tvFee.setTextColor(Res.getColor(helper.itemView.getContext(), R.color.color_c5602d));
            tvStatus.setTextColor(Res.getColor(helper.itemView.getContext(), R.color.color_c5602d));
        } else if (TextUtils.equals("ENABLE", item.withdraw_status)) {

        } else if (TextUtils.equals("DISABLE", item.withdraw_status)) {

        } else if (TextUtils.equals("FINISH", item.withdraw_status)) {
            tvStatus.setText("提现成功");
            tvFee.setTextColor(Res.getColor(helper.itemView.getContext(), R.color.color_b6a57b));
        } else if (TextUtils.equals("FAIL", item.withdraw_status)) {
            tvStatus.setText("提现失败(点击查看原因)");
            tvFee.setTextColor(Res.getColor(helper.itemView.getContext(), R.color.color_333333));
            tvStatus.setTextColor(Res.getColor(helper.itemView.getContext(), R.color.color_c52d2d));

            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommonTipDialog commonTipDialog = new CommonTipDialog(helper.itemView.getContext());
                    commonTipDialog
                            .setTitle("提示")
                            .setLeftButton("关闭")
                            .setOnLeftButtonClick(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    commonTipDialog.dismiss();
                                }
                            })
                            .setContent(item.fail_reason + "")
                            .show();
                }
            });
        }
    }
}