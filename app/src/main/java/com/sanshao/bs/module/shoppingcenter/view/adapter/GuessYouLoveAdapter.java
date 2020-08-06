package com.sanshao.bs.module.shoppingcenter.view.adapter;

import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.util.Constants;
import com.sanshao.bs.util.GlideUtil;
import com.sanshao.bs.util.MathUtil;
import com.sanshao.bs.widget.OvalImageView;

/**
 * @Author yuexingxing
 * @time 2020/7/1
 */
public class GuessYouLoveAdapter extends BaseQuickAdapter<GoodsDetailInfo, BaseViewHolder> {

    public GuessYouLoveAdapter() {
        super(R.layout.item_layout_guess_you_love, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailInfo item) {
        helper.setText(R.id.tv_title, item.sarti_name);

        View viewLeft = helper.getView(R.id.view_left);
        View viewRight = helper.getView(R.id.view_right);
        if (helper.getAdapterPosition() % 2 == 0) {
            viewLeft.setVisibility(View.GONE);
            viewRight.setVisibility(View.VISIBLE);
        } else {
            viewLeft.setVisibility(View.VISIBLE);
            viewRight.setVisibility(View.GONE);
        }
        TextView tvOldPrice = helper.getView(R.id.tv_old_price);
        tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        helper.setText(R.id.tv_price, MathUtil.getNumExclude0(item.sarti_saleprice));
        helper.setText(R.id.tv_old_price, "¥" + MathUtil.getNumExclude0(item.sarti_saleprice));
        GlideUtil.loadImage(item.thumbnail_img, helper.getView(R.id.iv_icon));
    }
}
