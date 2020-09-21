package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.view.adapter;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.util.ContainerUtil;
import com.exam.commonbiz.util.ScreenUtil;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.SSApplication;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.widget.VideoPlayLayout;

import com.exam.commonbiz.util.GlideUtil;

import cn.sanshaoxingqiu.ssbm.util.MathUtil;

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

        TextView tvOldPrice = helper.getView(R.id.tv_old_price);
        tvOldPrice.setText("¥" + MathUtil.getNumExclude0(item.sarti_mkprice));
        tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

        if (item.isFree()) {
            helper.setText(R.id.btn_buy, "免费领取");
        } else if (item.isPayByPoint()) {
            tvOldPrice.setVisibility(View.GONE);
            helper.setText(R.id.btn_buy, "分享金购买");
        }
        helper.setText(R.id.tv_price, item.getPriceText());

        View viewInclude = helper.getView(R.id.include_video);
        ImageView ivIcon = helper.getView(R.id.iv_icon);
        VideoPlayLayout videoPlayLayout = helper.getView(R.id.video_play_layout);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewInclude.getLayoutParams();
        int videoHeight = ScreenUtil.getScreenWidth(SSApplication.app) - ScreenUtil.dp2px(SSApplication.app, 24);
        layoutParams.height = videoHeight;

        if (!ContainerUtil.isEmpty(item.sarti_img) && !TextUtils.isEmpty(item.sarti_img.get(0).video)) {
            ivIcon.setVisibility(View.GONE);
            int topRadius = ScreenUtil.dp2px(SSApplication.app, 10);
            videoPlayLayout.setRadius(topRadius, topRadius, 0, 0);
            videoPlayLayout.setVisibility(View.VISIBLE);
            videoPlayLayout.setVideoInfo(item.sarti_img.get(0));
        } else {
            ivIcon.setVisibility(View.VISIBLE);
            videoPlayLayout.setVisibility(View.GONE);
            GlideUtil.loadImage(item.thumbnail_img, ivIcon);
            //暂停播放
            if (!item.isPlay) {
                videoPlayLayout.pausePlay();
            }
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
