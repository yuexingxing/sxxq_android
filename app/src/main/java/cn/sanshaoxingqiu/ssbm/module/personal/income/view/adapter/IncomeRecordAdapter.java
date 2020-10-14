package cn.sanshaoxingqiu.ssbm.module.personal.income.view.adapter;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.util.ContainerUtil;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.IncomeInfo;

/**
 * @Author yuexingxing
 * @time 2020/10/12
 */
public class IncomeRecordAdapter extends BaseQuickAdapter<IncomeInfo, BaseViewHolder> {

    public IncomeRecordAdapter() {
        super(R.layout.item_layout_income_record, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, IncomeInfo item) {
        helper.setText(R.id.tv_time, item.income_date);

        RecyclerView recyclerView = helper.getView(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(helper.itemView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        IncomeRecordDetailAdapter incomeRecordDetailAdapter = new IncomeRecordDetailAdapter();
        recyclerView.setAdapter(incomeRecordDetailAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        if (!ContainerUtil.isEmpty(item.income)) {
            incomeRecordDetailAdapter.addData(item.income);
        }

        if (helper.getAdapterPosition() == 0) {
            helper.getView(R.id.ll_tip).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.ll_tip).setVisibility(View.GONE);
        }

        if (helper.getAdapterPosition() == getData().size() - 1) {
            helper.getView(R.id.view_space_bottom).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.view_space_bottom).setVisibility(View.GONE);
        }
    }
}
