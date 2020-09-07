package cn.sanshaoxingqiu.ssbm.module.personal.inquiry.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.util.GlideUtil;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.module.order.bean.OrderInfo;

import cn.sanshaoxingqiu.ssbm.util.Constants;

/**
 * @Author yuexingxing
 * @time 2020/7/21
 */
public class ToBeInquiryListAdapter extends BaseQuickAdapter<OrderInfo, BaseViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    public ToBeInquiryListAdapter() {
        super(R.layout.item_layout_tobe_inquiry, null);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderInfo item) {
        helper.setText(R.id.tv_title, helper.getAdapterPosition() + "-" + item.name);

        helper.getView(R.id.tv_city).setVisibility(View.GONE);
        helper.getView(R.id.ll_viewcode).setVisibility(View.VISIBLE);
        helper.getView(R.id.ll_right_price).setVisibility(View.GONE);
        helper.getView(R.id.tv_price).setVisibility(View.VISIBLE);

        helper.setText(R.id.tv_price, "服务次数: 1次");
        ImageView imgIcon = helper.getView(R.id.iv_icon);
        GlideUtil.loadImage(Constants.DEFAULT_IMG_URL, imgIcon);
        if (helper.getAdapterPosition() == 0) {
            helper.getView(R.id.view_space_top).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.view_space_top).setVisibility(View.GONE);
        }

        helper.getView(R.id.rl_bg).setOnClickListener(view -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(item);
            }
        });

        helper.getView(R.id.ll_viewcode).setOnClickListener(view -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onViewCodeClick(item);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(OrderInfo item);

        void onViewCodeClick(OrderInfo item);
    }
}
