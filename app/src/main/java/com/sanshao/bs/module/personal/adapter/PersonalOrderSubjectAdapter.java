package com.sanshao.bs.module.personal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sanshao.bs.R;
import com.sanshao.bs.module.order.bean.OrderInfo;

/**
 * @Author yuexingxing
 * @time 2020/7/14
 */
public class PersonalOrderSubjectAdapter extends BaseQuickAdapter<OrderInfo, BaseViewHolder> {

    public PersonalOrderSubjectAdapter() {
        super(R.layout.item_layout_personal_order_subject, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderInfo item) {
        helper.setText(R.id.tv_title, helper.getAdapterPosition() + "-" + item.name);
    }
}
