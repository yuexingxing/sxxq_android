package com.sanshao.bs.module.shoppingcenter.view;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.FragmentLayoutGoodsDetailVideoBinding;
import com.sanshao.bs.util.Constants;

/**
 * 商品详情-视频播放
 *
 * @Author yuexingxing
 * @time 2020/7/28
 */
public class GoodsDetailVideoFragment extends BaseFragment<BaseViewModel, FragmentLayoutGoodsDetailVideoBinding> {

    public static GoodsDetailVideoFragment newInstance() {
        GoodsDetailVideoFragment fragment = new GoodsDetailVideoFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_layout_goods_detail_video;
    }

    @Override
    public void initData() {
        binding.videoPlayLayout.setVideoPlayUrl(Constants.VIDEO_PLAY_URL);
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