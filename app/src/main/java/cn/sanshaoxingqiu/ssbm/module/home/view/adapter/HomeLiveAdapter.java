package cn.sanshaoxingqiu.ssbm.module.home.view.adapter;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.GlideUtil;
import com.exam.commonbiz.util.ScreenUtil;
import com.exam.commonbiz.util.StatusBarUtil;
import com.sanshao.livemodule.liveroom.IMLVBLiveRoomListener;
import com.sanshao.livemodule.liveroom.MLVBLiveRoom;
import com.sanshao.livemodule.liveroom.roomutil.bean.VideoInfo;
import com.sanshao.livemodule.zhibo.common.report.TCELKReportMgr;
import com.sanshao.livemodule.zhibo.common.utils.TCConstants;
import com.sanshao.livemodule.zhibo.login.TCUserMgr;
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

        MLVBLiveRoom mLiveRoom = MLVBLiveRoom.sharedInstance(helper.itemView.getContext());
        TXCloudVideoView txCloudVideoView = helper.getView(R.id.anchor_video_view);
        txCloudVideoView.setLogMargin(10, 10, 45, 55);
        mLiveRoom.enterRoom(item.room_id, item.rtmp_pull_url, txCloudVideoView, new IMLVBLiveRoomListener.EnterRoomCallback() {
            @Override
            public void onError(int errCode, String errInfo) {
                Log.e(TAG, "加入房间失败--" + item.room_id + "/" + errInfo);
                txCloudVideoView.setVisibility(View.GONE);
                helper.getView(R.id.iv_bg).setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess() {
                Log.e(TAG, "加入房间成功--" + item.room_id);
                helper.getView(R.id.iv_bg).setVisibility(View.GONE);
                txCloudVideoView.setVisibility(View.VISIBLE);
            }
        });
    }
}
