package com.sanshao.bs.module.order.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sanshao.bs.R;
import com.sanshao.bs.module.order.bean.OrderInfo;

/**
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class RemainingServiceAdapter extends BaseQuickAdapter<OrderInfo, BaseViewHolder> {

    public RemainingServiceAdapter() {
        super(R.layout.item_layout_remaining_service, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderInfo item) {
        helper.setText(R.id.tv_title, helper.getAdapterPosition() + "-" + item.name);
    }
}
