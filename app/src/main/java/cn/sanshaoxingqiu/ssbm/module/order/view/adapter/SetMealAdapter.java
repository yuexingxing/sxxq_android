package cn.sanshaoxingqiu.ssbm.module.order.view.adapter;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;

import com.exam.commonbiz.util.GlideUtil;

import cn.sanshaoxingqiu.ssbm.util.MathUtil;

/**
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class SetMealAdapter extends BaseQuickAdapter<GoodsDetailInfo, BaseViewHolder> {

    private int optType;

    public SetMealAdapter() {
        super(R.layout.item_layout_confirm_order_set_meal, null);
    }

    public void setOptType(int type) {
        optType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailInfo item) {
        helper.setText(R.id.tv_title, item.sarti_name);
        helper.setText(R.id.tv_count, "x" + (TextUtils.isEmpty(item.use_qty) ? "1" : item.use_qty));
        if (ConfirmOrderAdapter.OPT_TYPE_ORDER_LIST == optType) {
            helper.setText(R.id.tv_price, "¥" + MathUtil.getNumExclude0(item.unit_price));
        } else {
            helper.setText(R.id.tv_price, "¥" + MathUtil.getNumExclude0(item.sarti_saleprice));
        }
        TextView tvOldPrice = helper.getView(R.id.tv_old_price);
        tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        GlideUtil.loadImage(item.thumbnail_img, helper.getView(R.id.iv_icon));
        tvOldPrice.setText("¥" + MathUtil.getNumExclude0(item.sarti_mkprice));

        if (helper.getAdapterPosition() == 0) {
            helper.getView(R.id.view_space_top).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.view_space_top).setVisibility(View.VISIBLE);
        }
    }
}
