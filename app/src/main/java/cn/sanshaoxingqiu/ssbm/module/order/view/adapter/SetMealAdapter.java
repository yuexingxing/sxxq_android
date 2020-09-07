package cn.sanshaoxingqiu.ssbm.module.order.view.adapter;

import android.graphics.Paint;
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

    public SetMealAdapter() {
        super(R.layout.item_layout_confirm_order_set_meal, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailInfo item) {
        helper.setText(R.id.tv_title, item.sarti_name);
        helper.setText(R.id.tv_price, "¥" + MathUtil.getNumExclude0(item.sarti_saleprice));
        helper.setText(R.id.tv_count, "x" + item.use_qty);
        TextView tvOldPrice = helper.getView(R.id.tv_old_price);
        tvOldPrice.setText("¥" + MathUtil.getNumExclude0(item.sarti_mkprice));
        tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        GlideUtil.loadImage(item.thumbnail_img, helper.getView(R.id.iv_icon));

        if (helper.getAdapterPosition() == 0) {
            helper.getView(R.id.view_space_top).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.view_space_top).setVisibility(View.VISIBLE);
        }
    }
}
