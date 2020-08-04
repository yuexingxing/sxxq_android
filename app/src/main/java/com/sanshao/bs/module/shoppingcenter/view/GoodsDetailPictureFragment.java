package com.sanshao.bs.module.shoppingcenter.view;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.FragmentLayoutGoodsDetailPictureBinding;
import com.sanshao.bs.util.Constants;
import com.sanshao.bs.util.GlideUtil;

/**
 * 商品详情-图片预览
 *
 * @Author yuexingxing
 * @time 2020/7/28
 */
public class GoodsDetailPictureFragment extends BaseFragment<BaseViewModel, FragmentLayoutGoodsDetailPictureBinding> {

    public static GoodsDetailPictureFragment newInstance(String url) {
        GoodsDetailPictureFragment fragment = new GoodsDetailPictureFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.OPT_DATA, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_layout_goods_detail_picture;
    }

    @Override
    public void initData() {
        if (getArguments() != null) {
            GlideUtil.loadImage(getArguments().getString(Constants.OPT_DATA), binding.ivIcon);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}