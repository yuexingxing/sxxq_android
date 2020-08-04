package com.sanshao.bs.module.shoppingcenter.view;

import android.os.Bundle;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.FragmentLayoutGoodsDetailVideoBinding;
import com.sanshao.bs.module.shoppingcenter.bean.VideoInfo;
import com.sanshao.bs.util.Constants;

/**
 * 商品详情-视频播放
 *
 * @Author yuexingxing
 * @time 2020/7/28
 */
public class GoodsDetailVideoFragment extends BaseFragment<BaseViewModel, FragmentLayoutGoodsDetailVideoBinding> {

    public static GoodsDetailVideoFragment newInstance(VideoInfo videoInfo) {
        GoodsDetailVideoFragment fragment = new GoodsDetailVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.OPT_DATA, videoInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_layout_goods_detail_video;
    }

    @Override
    public void initData() {
        if (getArguments().getSerializable(Constants.OPT_DATA) != null) {
            VideoInfo videoInfo = (VideoInfo) getArguments().getSerializable(Constants.OPT_DATA);
            binding.videoPlayLayout.setVideoInfo(videoInfo);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.videoPlayLayout.pausePlay();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.videoPlayLayout.pausePlay();
    }
}