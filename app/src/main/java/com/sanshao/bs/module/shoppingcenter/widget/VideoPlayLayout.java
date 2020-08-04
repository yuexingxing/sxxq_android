package com.sanshao.bs.module.shoppingcenter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.sanshao.bs.R;
import com.sanshao.bs.module.shoppingcenter.bean.VideoInfo;
import com.sanshao.bs.util.GlideUtil;

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
        mJzvdStd.setUp(videoInfo.video, "", JzvdStd.SCREEN_NORMAL);
        mJzvdStd.fullscreenButton.setVisibility(INVISIBLE);
        GlideUtil.loadImage(videoInfo.img, mJzvdStd.posterImageView);
    }

    public void pausePlay() {
        Jzvd.goOnPlayOnPause();
    }

    public void resumeVideo() {
        Jzvd.goOnPlayOnResume();
    }
}
