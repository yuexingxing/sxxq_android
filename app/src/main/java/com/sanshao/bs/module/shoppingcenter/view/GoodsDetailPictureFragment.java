package com.sanshao.bs.module.shoppingcenter.view;

import com.bumptech.glide.Glide;
import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.FragmentLayoutGoodsDetailPictureBinding;
import com.sanshao.bs.util.Constants;

/**
 * 商品详情-图片预览
 *
 * @Author yuexingxing
 * @time 2020/7/28
 */
public class GoodsDetailPictureFragment extends BaseFragment<BaseViewModel, FragmentLayoutGoodsDetailPictureBinding> {

    public static GoodsDetailPictureFragment newInstance() {
        GoodsDetailPictureFragment fragment = new GoodsDetailPictureFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_layout_goods_detail_picture;
    }

    @Override
    public void initData() {
        Glide.with(SSApplication.app).load(Constants.DEFAULT_IMG_URL).into(binding.ivIcon);
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