package cn.sanshaoxingqiu.ssbm.module.personal.adapter;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yuexingxing
 * @time 2020/7/14
 */
public class PersonalOrderSubjectAdapter extends BaseQuickAdapter<OrderInfo, BaseViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private boolean showOpenView = true;

    public PersonalOrderSubjectAdapter() {
        super(R.layout.item_layout_personal_order_subject, null);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setShowOpenView(boolean showOpenView) {
        this.showOpenView = showOpenView;
    }

    public interface OnItemClickListener {
        void onOpenClick();
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderInfo item) {
        helper.setText(R.id.tv_time, helper.getAdapterPosition() + "-6天12小时9分");

        RecyclerView recyclerView = helper.getView(R.id.recycler_view);
        InquiryInfoAdapter inquiryInfoAdapter = new InquiryInfoAdapter(helper.getAdapterPosition() == getData().size() - 1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(inquiryInfoAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);

        if (showOpenView) {
            helper.getView(R.id.rl_open).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.rl_open).setVisibility(View.GONE);
        }

        if (helper.getAdapterPosition() == 0) {
            helper.getView(R.id.view_space_top).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.view_space_top).setVisibility(View.GONE);
        }

        if (helper.getAdapterPosition() == getData().size() - 1) {
            helper.getView(R.id.view_space_bottom).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.view_space_bottom).setVisibility(View.GONE);
        }

        helper.getView(R.id.rl_open).setOnClickListener(view -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onOpenClick();
            }
        });

        List<OrderInfo> orderInfoList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.name = "黄金微针你的美容必备，美容必备…";
            orderInfo.time = "周一至周五：10:00-22:00";
            orderInfo.address = "上海市静安区新闸路829号丽都新贵大厦304、305、306室上海市静安区新闸路829号丽都新贵大厦304、305、306室";
            orderInfoList.add(orderInfo);
        }
        inquiryInfoAdapter.addData(orderInfoList);
    }
}
