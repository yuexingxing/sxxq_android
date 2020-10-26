package com.sanshao.livemodule.shortvideo;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.livemodule.R;
import com.sanshao.livemodule.databinding.FragmentShortVideoBinding;

/**
 * 短视频播放界面
 *
 * @Author yuexingxing
 * @time 2020/10/26
 */
public class ShortVideoFragment extends BaseFragment<BaseViewModel, FragmentShortVideoBinding> {

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

    }
}