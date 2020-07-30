package com.sanshao.bs.module.shoppingcenter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.sanshao.bs.R;
import com.sanshao.bs.util.Constants;

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

    public void setVideoPlayUrl(String playUrl) {
        mJzvdStd.setVisibility(VISIBLE);
        mJzvdStd.setUp(playUrl,"", JzvdStd.SCREEN_NORMAL);
        mJzvdStd.fullscreenButton.setVisibility(INVISIBLE);
        Glide.with(this).load(Constants.DEFAULT_IMG_BG).into(mJzvdStd.posterImageView);
    }

    public void pausePlay() {
        Jzvd.goOnPlayOnPause();
    }

    public void resumeVideo() {
        Jzvd.goOnPlayOnResume();
    }
}
