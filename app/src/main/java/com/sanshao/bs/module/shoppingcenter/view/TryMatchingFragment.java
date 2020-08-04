package com.sanshao.bs.module.shoppingcenter.view;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.util.ScreenUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.FragmentTryMatchingBinding;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.util.Constants;
import com.sanshao.bs.util.GlideUtil;
import com.sanshao.bs.util.MathUtil;

/**
 * 试试这样搭配
 *
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class TryMatchingFragment extends BaseFragment<BaseViewModel, FragmentTryMatchingBinding> {

    public static TryMatchingFragment newInstance(GoodsDetailInfo goodsDetailInfo) {
        TryMatchingFragment fragment = new TryMatchingFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.OPT_DATA, goodsDetailInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_try_matching;
    }

    @Override
    public void initData() {

        GoodsDetailInfo goodsDetailInfo = (GoodsDetailInfo) getArguments().getSerializable(Constants.OPT_DATA);
        if (goodsDetailInfo != null) {
            binding.includeTry.tvTitle.setText(goodsDetailInfo.sarti_name);
            binding.includeTry.tvPrice.setText(MathUtil.getNumExclude0(goodsDetailInfo.sarti_saleprice));
            binding.includeTry.tvOldPrice.setText("¥" + MathUtil.getNumExclude0(goodsDetailInfo.sarti_mkprice));
            binding.includeTry.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            GlideUtil.loadImage(goodsDetailInfo.thumbnail_img, binding.includeTry.ivIcon);
            binding.includeTry.tvSellNum.setText("热卖" + goodsDetailInfo.sell_num + "件");

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.rlContent.getLayoutParams();
            if (goodsDetailInfo.position == 0) {
                layoutParams.setMargins(ScreenUtil.dp2px(context, 0), 0, 0, 0);
            } else {
                layoutParams.setMargins(ScreenUtil.dp2px(context, 12), 0, ScreenUtil.dp2px(context, 12), 0);
            }
            binding.rlContent.setLayoutParams(layoutParams);
        }
    }
}