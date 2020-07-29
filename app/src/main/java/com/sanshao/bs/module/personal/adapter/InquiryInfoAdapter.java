package com.sanshao.bs.module.personal.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sanshao.bs.R;
import com.sanshao.bs.module.order.bean.OrderInfo;

/**
 * @Author yuexingxing
 * @time 2020/7/21
 */
public class InquiryInfoAdapter extends BaseQuickAdapter<OrderInfo, BaseViewHolder> {

    private boolean isParentItemLastPosition;

    public InquiryInfoAdapter(boolean isParentItemLastPosition) {
        super(R.layout.item_layout_inquiry_info, null);
        this.isParentItemLastPosition = isParentItemLastPosition;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderInfo item) {
        helper.setText(R.id.tv_title, helper.getAdapterPosition() + "-" + item.name);
        helper.setText(R.id.tv_time, item.time);
        helper.setText(R.id.tv_address, item.address);

        if (helper.getAdapterPosition() == getData().size() - 1 && !isParentItemLastPosition) {
            helper.getView(R.id.view_space_bottom).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.view_space_bottom).setVisibility(View.GONE);
        }
    }
}
