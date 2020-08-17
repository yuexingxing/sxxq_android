package com.sanshao.bs.module.shoppingcenter.view.adapter;

import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.util.CommonCallBack;
import com.sanshao.bs.R;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.util.GlideUtil;
import com.sanshao.bs.util.MathUtil;

/**
 * @Author yuexingxing
 * @time 2020/6/16
 */
public class GoodsTypeDetailVerticalAdapter extends BaseMultiItemQuickAdapter<GoodsDetailInfo, BaseViewHolder> {

    private boolean showConver;//是否展示遮罩层
    private CommonCallBack mCommonCallBack;

    public GoodsTypeDetailVerticalAdapter() {
        super(null);
        addItemType(GoodsDetailInfo.GOODS_TYPE.REAL_DATA, R.layout.item_layout_goods_type_detail_vertical);
        addItemType(GoodsDetailInfo.GOODS_TYPE.WITH_LAST_DATA, R.layout.item_layout_goods_type_detail_vertical_empty);
    }

    public void setCommonCallBack(CommonCallBack commonCallBack){
        mCommonCallBack = commonCallBack;
    }

    public void isShowConver(boolean show) {
        showConver = show;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailInfo item) {
        if (helper.getItemViewType() == GoodsDetailInfo.GOODS_TYPE.REAL_DATA) {
            initView(helper, item);
        } else {
            initEmptyView(helper, item);
        }
    }

    private void initView(BaseViewHolder helper, GoodsDetailInfo item) {

        helper.setText(R.id.tv_title, item.sarti_name);
        TextView tvOldPrice = helper.getView(R.id.tv_old_price);
        tvOldPrice.setText("¥" + MathUtil.getNumExclude0(item.sarti_mkprice));
        tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        GlideUtil.loadImage(item.thumbnail_img, helper.getView(R.id.iv_icon));

        if (showConver) {
            helper.getView(R.id.view_conver).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.view_conver).setVisibility(View.GONE);
        }

        if (item.isFree()) {
            helper.setText(R.id.tv_price, "免费领取");
        } else if (item.isPayByPoint()) {
            helper.setText(R.id.tv_price, item.getPointTip());
            tvOldPrice.setVisibility(View.GONE);
        } else {
            helper.setText(R.id.tv_price, "¥" + MathUtil.getNumExclude0(item.sarti_saleprice));
        }

        if (helper.getAdapterPosition() % 2 == 0) {
            helper.getView(R.id.view_right).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.view_right).setVisibility(View.GONE);
        }
    }

    private void initEmptyView(BaseViewHolder helper, GoodsDetailInfo item) {

        helper.getView(R.id.iv_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCommonCallBack != null){
                    mCommonCallBack.callback(helper.getAdapterPosition(), item);
                }
            }
        });

        if (showConver) {
            helper.getView(R.id.view_conver).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.view_conver).setVisibility(View.GONE);
        }
    }
}
