package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.adapter;

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
 * @time 2020/6/16
 */
public class GoodsTypeDetailHorizontalAdapter extends BaseQuickAdapter<GoodsDetailInfo, BaseViewHolder> {

    public GoodsTypeDetailHorizontalAdapter() {
        super(R.layout.item_layout_goods_type_detail_horizontal, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailInfo item) {
        helper.setText(R.id.tv_title, item.sarti_name);

        TextView tvOldPrice = helper.getView(R.id.tv_old_price);
        tvOldPrice.setText("¥" + MathUtil.getNumExclude0(item.sarti_mkprice));
        tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        GlideUtil.loadImage(item.thumbnail_img, helper.getView(R.id.iv_icon));

        if (item.isPayByPoint()) {
            tvOldPrice.setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_price, item.getPriceText());

        if (helper.getAdapterPosition() == 0) {
            helper.getView(R.id.view_left).setVisibility(View.VISIBLE);
            helper.getView(R.id.view_right).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.view_left).setVisibility(View.VISIBLE);
            helper.getView(R.id.view_right).setVisibility(View.VISIBLE);
        }
    }
}
