package com.sanshao.bs.module.order.view;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.util.QRCodeUtil;
import com.exam.commonbiz.util.ScreenUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.FragmentViewCouponcodeDetailBinding;
import com.sanshao.bs.module.order.viewmodel.OrderStatusViewModel;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.util.Constants;

/**
 * 待支付
 *
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class ViewCouponCodeDetailFragment extends BaseFragment<OrderStatusViewModel, FragmentViewCouponcodeDetailBinding> {

    public ViewCouponCodeDetailFragment() {

    }

    public static ViewCouponCodeDetailFragment newInstance(GoodsDetailInfo.WriteOffInfo writeOffInfo) {
        ViewCouponCodeDetailFragment fragment = new ViewCouponCodeDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.OPT_DATA, writeOffInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view_couponcode_detail;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void initData() {

        GoodsDetailInfo.WriteOffInfo writeOffInfo = (GoodsDetailInfo.WriteOffInfo) getArguments().getSerializable(Constants.OPT_DATA);
        Bitmap bitmap = QRCodeUtil.createQRCodeBitmap(writeOffInfo.code, ScreenUtil.dp2px(context, 150), ScreenUtil.dp2px(context, 150));
        binding.ivQrcode.setImageBitmap(bitmap);
    }
}