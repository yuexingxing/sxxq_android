package com.sanshao.bs.module.personal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sanshao.bs.R;
import com.sanshao.bs.module.order.bean.OrderInfo;

/**
 * @Author yuexingxing
 * @time 2020/7/21
 */
public class ToBeInquiryAdapter extends BaseQuickAdapter<OrderInfo, BaseViewHolder> {

    public ToBeInquiryAdapter() {
        super(R.layout.item_layout_tobe_inquiry, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderInfo item) {
        helper.setText(R.id.tv_title, helper.getAdapterPosition() + "-" + item.name);
        helper.setText(R.id.tv_time, item.time);
        helper.setText(R.id.tv_address, item.address);
    }
}
