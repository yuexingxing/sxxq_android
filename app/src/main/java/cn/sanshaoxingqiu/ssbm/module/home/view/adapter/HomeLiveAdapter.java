package cn.sanshaoxingqiu.ssbm.module.home.view.adapter;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.GlideUtil;
import com.exam.commonbiz.util.ScreenUtil;
import com.exam.commonbiz.util.StatusBarUtil;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoInfo;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import cn.sanshaoxingqiu.ssbm.R;

public class HomeLiveAdapter extends BaseQuickAdapter<VideoInfo, BaseViewHolder> {

    private CommonCallBack mCommonCallBack;

    public HomeLiveAdapter() {
        super(R.layout.item_layout_home_live, null);
    }

    public void setCommonCallBack(CommonCallBack commonCallBack) {
        mCommonCallBack = commonCallBack;
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoInfo item) {

        helper.getView(R.id.iv_play).setVisibility(View.VISIBLE);
        GlideUtil.loadgifImage(R.drawable.image_liveanimation, helper.getView(R.id.iv_play));
        if (item.pushers != null) {
            helper.setText(R.id.tv_title, "@" + item.pushers.anchor_name);
        }
        helper.setText(R.id.tv_content, item.live_title);

        ImageView ivLiveBg = helper.getView(R.id.iv_bg);
        GlideUtil.loadImage(item.frontcover, ivLiveBg, R.drawable.image_graphofbooth_default);
        if (ivLiveBg.getTag() == null) {
            TXLivePlayer txLivePlayer = new TXLivePlayer(helper.itemView.getContext());
            ivLiveBg.setTag(txLivePlayer);
        }

        FrameLayout frameLayout = helper.getView(R.id.fl_content);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams.height = ScreenUtil.getScreenHeight(helper.itemView.getContext()) + StatusBarUtil.getStatusBarHeight(helper.itemView.getContext());
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCommonCallBack != null) {
                    mCommonCallBack.callback(helper.getAdapterPosition(), item);
                }
            }
        });

        TXCloudVideoView txCloudVideoView = helper.getView(R.id.anchor_video_view);
        txCloudVideoView.setTag(item);
    }

    public void stopPlay() {

    }
}
