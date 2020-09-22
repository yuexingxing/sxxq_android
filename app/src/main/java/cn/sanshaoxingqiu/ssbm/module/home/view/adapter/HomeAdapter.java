package cn.sanshaoxingqiu.ssbm.module.home.view.adapter;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.GlideUtil;
import com.exam.commonbiz.util.ScreenUtil;
import com.exam.commonbiz.util.StatusBarUtil;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoInfo;

import cn.sanshaoxingqiu.ssbm.R;

public class HomeAdapter extends BaseQuickAdapter<VideoInfo, BaseViewHolder> {

    public static final int VIDEO_TYPE_LIVE = 0;
    public static final int VIDEO_TYPE_BACK = 1;

    private int videoType;
    private CommonCallBack mCommonCallBack;

    public HomeAdapter(int optType) {
        super(R.layout.item_layout_home, null);
        videoType = optType;
    }

    public void setCommonCallBack(CommonCallBack commonCallBack) {
        mCommonCallBack = commonCallBack;
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoInfo item) {
        //直播
        if (VIDEO_TYPE_LIVE == videoType) {
            helper.getView(R.id.iv_play).setVisibility(View.VISIBLE);
            GlideUtil.loadgifImage(R.drawable.image_liveanimation, helper.getView(R.id.iv_play));
        }
        //回放
        else {
            helper.getView(R.id.iv_play).setVisibility(View.INVISIBLE);
        }
        if (item.pushers != null) {
            helper.setText(R.id.tv_title, "@" + item.pushers.anchor_name);
        }
        helper.setText(R.id.tv_content, item.live_title);
        GlideUtil.loadImage(item.frontcover, helper.getView(R.id.iv_bg), R.drawable.image_graphofbooth_default);

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
    }
}
