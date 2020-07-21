package com.sanshao.bs.module.personal.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sanshao.bs.R;
import com.sanshao.bs.module.order.bean.OrderInfo;
import com.sanshao.bs.module.order.view.adapter.OrderListAdapter;

import java.util.ArrayList;
import java.util.List;

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
        helper.setText(R.id.tv_time, helper.getAdapterPosition() + "-6天12小时9分");

        RecyclerView recyclerView = helper.getView(R.id.recycler_view);
        ToBeInquiryAdapter toBeInquiryAdapter = new ToBeInquiryAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(toBeInquiryAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);

        List<OrderInfo> orderInfoList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.name = "黄金微针你的美容必备，美容必备…";
            orderInfo.time = "周一至周五：10:00-22:00";
            orderInfo.address = "上海市静安区新闸路829号丽都新贵大厦304、305、306室上海市静安区新闸路829号丽都新贵大厦304、305、306室";
            orderInfoList.add(orderInfo);
        }
        toBeInquiryAdapter.addData(orderInfoList);
    }
}
