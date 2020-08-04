package com.sanshao.bs.module.shoppingcenter.view.adapter;

import android.graphics.Paint;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sanshao.bs.R;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.util.GlideUtil;
import com.sanshao.bs.util.MathUtil;

/**
 * @Author yuexingxing
 * @time 2020/6/30
 */
public class SetMealAdapter extends BaseQuickAdapter<GoodsDetailInfo, BaseViewHolder> {

    public SetMealAdapter() {
        super(R.layout.item_layout_set_meal, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailInfo item) {

        helper.setText(R.id.tv_title, item.sarti_name);
        helper.setText(R.id.tv_price, MathUtil.getNumExclude0(item.sarti_saleprice));
        CheckBox checkBox = helper.getView(R.id.checkbox);
        checkBox.setChecked(item.checked);

        helper.setText(R.id.tv_sell_num, "热卖" + item.sell_num + "件");
        TextView tvOldPrice = helper.getView(R.id.tv_old_price);
        tvOldPrice.setText("¥" + MathUtil.getNumExclude0(item.sarti_mkprice));
        tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        GlideUtil.loadImage(item.thumbnail_img, (ImageView) helper.getView(R.id.iv_icon));
    }
}
