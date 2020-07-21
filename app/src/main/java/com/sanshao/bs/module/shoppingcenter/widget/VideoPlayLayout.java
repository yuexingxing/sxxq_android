package com.sanshao.bs.module.shoppingcenter.widget;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.exam.commonbiz.util.MediaPlayerManager;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.util.Constants;

import java.util.HashMap;
import java.util.Timer;

public class VideoPlayLayout extends LinearLayout {
    private final String TAG = VideoPlayLayout.class.getSimpleName();
    private SurfaceHolder mSurfaceHolder;
    private SurfaceView mSurfaceView;
    private Timer mTimer = new Timer();
    private String mVideoPlayUrl;
    private ImageView mIvPreview;
    private boolean isInitFinish = false;
    private ImageView mIvPlay;
    private MediaPlayerManager mMediaPlayerManager;

    public VideoPlayLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_layout_video_play, this);
        initView(context);
    }

    public void initView(Context context) {

        mIvPlay = findViewById(R.id.iv_play);
        mIvPreview = findViewById(R.id.iv_preview);
        mSurfaceView = findViewById(R.id.video_play_surfaceview);
        mMediaPlayerManager = MediaPlayerManager.getInstance(context);
        mMediaPlayerManager.setmOnMediaHelperPauseListener(new MediaPlayerManager.OnMediaHelperPauseListener() {
            @Override
            public void showPauseImg() {
                mIvPreview.setVisibility(VISIBLE);
            }
        });
        mIvPlay.setOnClickListener(v -> {
            if (mMediaPlayerManager == null){
                return;
            }
            mIvPreview.setVisibility(View.GONE);
            if (!mMediaPlayerManager.isPlaying()) {
                mIvPlay.setVisibility(View.GONE);
                mIvPlay.setImageResource(R.drawable.icon_video_pause);
                startPlay();
            }else{
                mIvPlay.setVisibility(View.VISIBLE);
                mIvPlay.setImageResource(R.drawable.icon_video_play);
                pausePlay();
            }
        });
        mSurfaceView.setOnClickListener(v -> {
            if (mIvPlay.getVisibility() == View.GONE) {
                mIvPlay.setVisibility(View.VISIBLE);
            } else {
                mIvPlay.setVisibility(View.GONE);
            }
        });
        Glide.with(SSApplication.app).load(Constants.DEFAULT_IMG_URL).into(mIvPreview);
    }

    public void setVideoPlayUrl(String playUrl){
        mVideoPlayUrl = playUrl;
        initSurfaceviewStateListener();
    }

    private void initSurfaceviewStateListener() {

        mMediaPlayerManager.setOnVideoSizeChangedListener((mp, width, height) -> changeVideoSize());
        mMediaPlayerManager.setmOnMediaHelperCompletionListener(new MediaPlayerManager.OnMediaHelperCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (mMediaPlayerManager == null) {
                    return;
                }
                mMediaPlayerManager.setDisplay(holder);
                setPlayVideo(mVideoPlayUrl);//添加播放视频的路径
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
//                freePlayer();
//                mSurfaceView.setVisibility(GONE);
                mIvPreview.setVisibility(VISIBLE);
                mIvPlay.setVisibility(VISIBLE);
                mTimer.cancel();
            }
        });
    }

    private void setPlayVideo(String path) {

        mMediaPlayerManager.setPath(path);
        mMediaPlayerManager.setmOnMediaHelperPrepareListener(new MediaPlayerManager.OnMediaHelperPrepareListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                isInitFinish = true;
            }
        });
//        try {
//            mMediaPlayer.setDataSource(path);//
//            mMediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);//缩放模式
//            mMediaPlayer.setLooping(false);//设置循环播放
//            mMediaPlayer.prepareAsync();//异步准备
////            mMediaPlayer.prepare();//同步准备,因为是同步在一些性能较差的设备上会导致UI卡顿
//            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { //准备完成回调
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    isInitFinish = true;
////                    mp.start();
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    //获取网络视频第一帧
    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    public void startPlay() {
        if (!mMediaPlayerManager.isPlaying()) {
            mMediaPlayerManager.start();
        }
    }

    public void stopPlay() {
        if (mMediaPlayerManager.isPlaying()) {
            mMediaPlayerManager.stopPlay();
        }
    }

    public void pausePlay() {
        if (mMediaPlayerManager != null && mMediaPlayerManager.isPlaying()) {
            mMediaPlayerManager.pause();
        }
    }

    public void freePlayer(){
        if (mMediaPlayerManager != null){
            mMediaPlayerManager.freePlayer();
        }
    }

    //改变视频的尺寸自适应。
    public void changeVideoSize() {
        int videoWidth = mMediaPlayerManager.getVideoWidth();
        int videoHeight = mMediaPlayerManager.getVideoHeight();

        int surfaceWidth = mSurfaceView.getWidth();
        int surfaceHeight = mSurfaceView.getHeight();

        //根据视频尺寸去计算->视频可以在sufaceView中放大的最大倍数。
        float max;
        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            //竖屏模式下按视频宽度计算放大倍数值
            max = Math.max((float) videoWidth / (float) surfaceWidth, (float) videoHeight / (float) surfaceHeight);
        } else {
            //横屏模式下按视频高度计算放大倍数值
            max = Math.max(((float) videoWidth / (float) surfaceHeight), (float) videoHeight / (float) surfaceWidth);
        }

        //视频宽高分别/最大倍数值 计算出放大后的视频尺寸
        videoWidth = (int) Math.ceil((float) videoWidth / max);
        videoHeight = (int) Math.ceil((float) videoHeight / max);

        //无法直接设置视频尺寸，将计算出的视频尺寸设置到surfaceView 让视频自动填充。
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(videoWidth, videoHeight);
//        params.addRule(RelativeLayout.CENTER_VERTICAL, binding.llContainer.getId());
        mSurfaceView.setLayoutParams(params);

        mIvPreview.setLayoutParams(params);
//        mIvPreview.setImageBitmap(getNetVideoBitmap(mVideoPlayUrl));
    }
}
