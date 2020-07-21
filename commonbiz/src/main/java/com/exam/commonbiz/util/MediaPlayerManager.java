package com.exam.commonbiz.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.view.SurfaceHolder;

import com.exam.commonbiz.log.XLog;

import java.io.IOException;

public class MediaPlayerManager {
    private final String TAG = MediaPlayerManager.class.getSimpleName();
    private static MediaPlayerManager instance;
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private OnMediaHelperPrepareListener mOnMediaHelperPrepareListener;
    private OnMediaHelperCompletionListener mOnMediaHelperCompletionListener;
    private OnMediaHelperPauseListener mOnMediaHelperPauseListener;
    private OnInitMusicListener mOnInitMusicListener;
    private OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private int mResID=-5;

    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener onVideoSizeChangedListener){
        mOnVideoSizeChangedListener = onVideoSizeChangedListener;
    }

    public void setmOnInitMusicListener(OnInitMusicListener mOnInitMusicListener) {
        this.mOnInitMusicListener = mOnInitMusicListener;
    }

    public void setmOnMediaHelperPauseListener(OnMediaHelperPauseListener mOnMediaHelperPauseListener) {
        this.mOnMediaHelperPauseListener = mOnMediaHelperPauseListener;
    }

    public void setmOnMediaHelperPrepareListener(OnMediaHelperPrepareListener mOnMediaHelperPrepareListener) {
        this.mOnMediaHelperPrepareListener = mOnMediaHelperPrepareListener;
    }

    public void setmOnMediaHelperCompletionListener(OnMediaHelperCompletionListener mOnMediaHelperCompletionListener) {
        this.mOnMediaHelperCompletionListener = mOnMediaHelperCompletionListener;
    }

    public static MediaPlayerManager getInstance(Context context) {
        if(instance==null){
            synchronized (MediaPlayerManager.class){
                if(instance==null){
                    instance=new MediaPlayerManager(context);
                }
            }
        }
        return instance;
    }

    private MediaPlayerManager(Context context){
        mContext=context;
        mMediaPlayer=new MediaPlayer();
    }

    public void setDisplay(SurfaceHolder holder){
        mMediaPlayer.setDisplay(holder);
    }

    /**
     * 当播放本地uri中音时调用
     * @param path
     */
    public void setPath(String path){
        if (TextUtils.isEmpty(path)){
            return;
        }
        if(mMediaPlayer.isPlaying()){
            mMediaPlayer.reset();
        }
        try {
            mMediaPlayer.setDataSource(mContext, Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.prepareAsync();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if(mOnMediaHelperPrepareListener !=null){
                    mOnMediaHelperPrepareListener.onPrepared(mp);
                }
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(mOnMediaHelperPrepareListener !=null){
                    mOnMediaHelperCompletionListener.onCompletion(mp);
                }
            }
        });
        mMediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
                if(mOnMediaHelperPrepareListener !=null){
                    mOnVideoSizeChangedListener.onVideoSizeChangedListener(mediaPlayer, i, i1);
                }
            }
        });
    }

    public int getVideoWidth(){
      return  mMediaPlayer.getVideoWidth();
    }

    public int getVideoHeight(){
        return  mMediaPlayer.getVideoHeight();
    }

    /**
     * 当调用raw下的文件时使用
     * @param resid
     */
    public void setRawFile(int resid){
        if(resid==mResID&&mResID!=-5){
            //相同音乐id或者且不是第一次播放，就直接返回
            return;
        }
        mOnInitMusicListener.initMode();
        mResID=resid;
        final AssetFileDescriptor afd = mContext.getResources().openRawResourceFd(resid);
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if(mOnMediaHelperPrepareListener !=null){
                    mOnMediaHelperPrepareListener.onPrepared(mp);
                    try {
                        afd.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(mOnMediaHelperPrepareListener !=null){
                    mOnMediaHelperCompletionListener.onCompletion(mp);
                }
            }
        });
    }

    public void start(){
        if(mMediaPlayer.isPlaying()){
            return;
        }
        mMediaPlayer.start();
    }

    public void pause(){
        if(!mMediaPlayer.isPlaying()){
            return;
        }
        mMediaPlayer.pause();
    }

    public boolean isPlaying(){
        if(mMediaPlayer!=null&&mMediaPlayer.isPlaying()){
            return true;
        }
        return false;
    }

    public void stopPlay() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
    }

    public int getCurrentPosition(){
        return mMediaPlayer.getCurrentPosition();
    }

    public int getDuration(){
        return mMediaPlayer.getDuration();
    }

    public void seekTo(int progress){
        mMediaPlayer.seekTo(progress);
    }

    public void freePlayer() {
        if (mMediaPlayer == null)
            return;
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        //player.reset();//清空所有设置
        mMediaPlayer.release();
        mMediaPlayer = null;
        XLog.d(TAG, "freePlayer-释放资源");
    }

    public interface OnMediaHelperCompletionListener{
        void onCompletion(MediaPlayer mp);
    }

    public interface OnMediaHelperPrepareListener {
        void onPrepared(MediaPlayer mp);
    }

    public interface OnMediaHelperPauseListener {
        void showPauseImg();
    }

    public interface OnInitMusicListener{
        void initMode();
    }

    public interface OnVideoSizeChangedListener{
        void onVideoSizeChangedListener(MediaPlayer mediaPlayer, int i, int i1);
    }
}



