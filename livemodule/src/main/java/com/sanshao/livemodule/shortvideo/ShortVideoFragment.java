package com.sanshao.livemodule.shortvideo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.util.Constants;
import com.sanshao.livemodule.R;
import com.sanshao.livemodule.databinding.FragmentShortVideoBinding;
import com.sanshao.livemodule.liveroom.MLVBLiveRoomImpl;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLivePlayer;

/**
 * 短视频播放界面
 *
 * @Author yuexingxing
 * @time 2020/10/26
 */
public class ShortVideoFragment extends BaseFragment<BaseViewModel, FragmentShortVideoBinding> implements ITXLivePlayListener {

    protected TXLivePlayer mTXLivePlayer;

    public static ShortVideoFragment newInstance() {
        ShortVideoFragment fragment = new ShortVideoFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_short_video;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void initData() {

        if (MLVBLiveRoomImpl.mInstance == null) {
            MLVBLiveRoomImpl.sharedInstance(context);
        }
        mTXLivePlayer = MLVBLiveRoomImpl.mInstance.mTXLivePlayer;

        mTXLivePlayer.setPlayerView(binding.txCloudVideoView);
        mTXLivePlayer.startPlay(Constants.VIDEO_PLAY_URL, TXLivePlayer.PLAY_TYPE_VOD_MP4);
        mTXLivePlayer.setPlayListener(this);

        binding.ivBg.setVisibility(View.GONE);
        binding.txCloudVideoView.setVisibility(View.VISIBLE);

//        binding.rlTopBg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mTXLivePlayer.isPlaying()) {
//                    mTXLivePlayer.pause();
//                    binding.ivPlay.setImageResource(R.drawable.play_start);
//                    binding.ivPlay.setVisibility(View.VISIBLE);
//                } else {
//                    mTXLivePlayer.resume();
//                    binding.ivPlay.setImageResource(R.drawable.play_pause);
//                    binding.ivPlay.setVisibility(View.GONE);
//                }
//            }
//        });

        binding.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        Log.d(TAG, "ShortVideoFragment-onVisible");
        if (mTXLivePlayer != null) {
            mTXLivePlayer.resume();
            Log.d(TAG, "ShortVideoFragment-直播播放了");
        }
    }

    @Override
    protected void onInVisible() {
        Log.d(TAG, "ShortVideoFragment-onInVisible");
        if (mTXLivePlayer != null) {
            mTXLivePlayer.pause();
            Log.d(TAG, "ShortVideoFragment-直播暂停播放了");
        }
    }

    @Override
    public void onPlayEvent(int i, Bundle bundle) {

    }

    @Override
    public void onNetStatus(Bundle bundle) {

    }
}