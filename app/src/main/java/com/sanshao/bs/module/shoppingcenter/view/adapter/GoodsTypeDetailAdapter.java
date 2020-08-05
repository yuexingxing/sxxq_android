package com.sanshao.bs.module.shoppingcenter.view.adapter;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.util.MathUtil;

/**
 * @Author yuexingxing
 * @time 2020/6/16
 */
public class GoodsTypeDetailAdapter extends BaseQuickAdapter<GoodsDetailInfo, BaseViewHolder> {

    public GoodsTypeDetailAdapter() {
        super(R.layout.item_layout_goods_type_detail, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailInfo item) {
        helper.setText(R.id.tv_title, item.sarti_name);

        helper.setText(R.id.tv_price, MathUtil.getNumExclude0(item.sarti_saleprice));
        TextView tvOldPrice = helper.getView(R.id.tv_old_price);
        tvOldPrice.setText("¥" + MathUtil.getNumExclude0(item.sarti_mkprice));
        tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        Glide.with(SSApplication.app).load(item.thumbnail_img).into((ImageView) helper.getView(R.id.iv_icon));

        if (helper.getAdapterPosition() == 0){
            helper.getView(R.id.view_left).setVisibility(View.VISIBLE);
        }else{
            helper.getView(R.id.view_left).setVisibility(View.GONE);
        }

        if (helper.getAdapterPosition() == getData().size() - 1){
            helper.getView(R.id.view_right).setVisibility(View.VISIBLE);
        }else{
            helper.getView(R.id.view_right).setVisibility(View.GONE);
        }
    }
}
