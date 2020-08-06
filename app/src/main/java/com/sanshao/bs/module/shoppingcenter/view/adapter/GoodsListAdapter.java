package com.sanshao.bs.module.shoppingcenter.view.adapter;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.util.ContainerUtil;
import com.exam.commonbiz.util.ScreenUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.bean.VideoInfo;
import com.sanshao.bs.module.shoppingcenter.widget.VideoPlayLayout;
import com.sanshao.bs.util.Constants;
import com.sanshao.bs.util.MathUtil;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class GoodsListAdapter extends BaseQuickAdapter<GoodsDetailInfo, BaseViewHolder> {

    private OnItemClickListener mCallBack;

    public interface OnItemClickListener {
        void onBuyClick(GoodsDetailInfo goodsDetailInfo);

        void onGoToDetail(GoodsDetailInfo goodsDetailInfo);

        void onShareClick(GoodsDetailInfo goodsDetailInfo);

        void onConsultClick();
    }

    public GoodsListAdapter() {
        super(R.layout.item_layout_goods_list, null);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mCallBack = onItemClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailInfo item) {
        helper.setText(R.id.tv_title, item.sarti_name);

        helper.setText(R.id.tv_price, MathUtil.getNumExclude0(item.sarti_saleprice));
        TextView tvOldPrice = helper.getView(R.id.tv_old_price);
        tvOldPrice.setText("¥" + MathUtil.getNumExclude0(item.sarti_mkprice));
        tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        View viewInclude = helper.getView(R.id.include_video);
        ImageView ivIcon = helper.getView(R.id.iv_icon);
        VideoPlayLayout videoPlayLayout = helper.getView(R.id.video_play_layout);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewInclude.getLayoutParams();
        int videoHeight = ScreenUtil.getScreenWidth(SSApplication.app) - ScreenUtil.dp2px(SSApplication.app, 24);
        layoutParams.height = videoHeight;

        if (ContainerUtil.isEmpty(item.sarti_img)) {
            ivIcon.setVisibility(View.VISIBLE);
            videoPlayLayout.setVisibility(View.GONE);
            Glide.with(SSApplication.app).load(item.thumbnail_img).into(ivIcon);
            //暂停播放
            if (!item.isPlay) {
                videoPlayLayout.pausePlay();
            }
        } else {
            ivIcon.setVisibility(View.GONE);
            videoPlayLayout.setVisibility(View.VISIBLE);
            videoPlayLayout.setVideoInfo(item.sarti_img.get(0));
        }
        viewInclude.setLayoutParams(layoutParams);

        helper.getView(R.id.ll_bg).setOnClickListener(v -> {
            if (mCallBack != null) {
                mCallBack.onGoToDetail(item);
            }
        });
        helper.getView(R.id.ll_consult).setOnClickListener(v -> {
            if (mCallBack != null) {
                mCallBack.onConsultClick();
            }
        });
        helper.getView(R.id.ll_share).setOnClickListener(v -> {
            if (mCallBack != null) {
                mCallBack.onShareClick(item);
            }
        });
        helper.getView(R.id.btn_buy).setOnClickListener(v -> {
            if (mCallBack != null) {
                mCallBack.onBuyClick(item);
            }
        });

        if (helper.getAdapterPosition() == getData().size() - 1) {
            helper.getView(R.id.view_bottom).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.view_bottom).setVisibility(View.GONE);
        }
    }

    public void stopPlay() {

    }
}
