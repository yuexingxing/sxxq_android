package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.VideoInfo;
import cn.sanshaoxingqiu.ssbm.util.GlideUtil;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoPlayLayout extends LinearLayout {
    private JzvdStd mJzvdStd;

    public VideoPlayLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_layout_video_play, this);
        initView();
    }

    public void initView() {
        mJzvdStd = findViewById(R.id.videoplayer);
    }

    public void setVideoInfo(VideoInfo videoInfo) {
        mJzvdStd.setVisibility(VISIBLE);
        mJzvdStd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP);
        mJzvdStd.setUp(videoInfo.video, "", JzvdStd.SCREEN_NORMAL);
        mJzvdStd.fullscreenButton.setVisibility(INVISIBLE);
        GlideUtil.loadImage(videoInfo.img, mJzvdStd.posterImageView);
    }

    public void setRadius(int topLeftRadius, int topRightRadius, int bottomLeftRaidus, int bottomRightRadius) {
        if (mJzvdStd != null) {
            mJzvdStd.setLeftTopRightBottom(topLeftRadius, topRightRadius, bottomLeftRaidus, bottomRightRadius);
        }
    }

    public void pausePlay() {
        Jzvd.goOnPlayOnPause();
    }

    public void resumeVideo() {
        Jzvd.goOnPlayOnResume();
    }
}
