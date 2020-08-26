package cn.sanshaoxingqiu.ssbm.module.order.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderInfo;

/**
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class ServedAdapter extends BaseQuickAdapter<OrderInfo, BaseViewHolder> {

    public ServedAdapter() {
        super(R.layout.item_layout_served, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderInfo item) {
        helper.setText(R.id.tv_title, helper.getAdapterPosition() + "-" + item.name);
    }
}
