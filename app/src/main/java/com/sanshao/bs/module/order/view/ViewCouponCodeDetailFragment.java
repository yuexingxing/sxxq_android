package com.sanshao.bs.module.order.view;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.util.QRCodeUtil;
import com.exam.commonbiz.util.ScreenUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.FragmentViewCouponcodeDetailBinding;
import com.sanshao.bs.module.order.bean.OrderInfo;
import com.sanshao.bs.module.order.viewmodel.OrderStatusViewModel;

/**
 * 待支付
 *
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class ViewCouponCodeDetailFragment extends BaseFragment<OrderStatusViewModel, FragmentViewCouponcodeDetailBinding> {

    public ViewCouponCodeDetailFragment() {

    }

    public static ViewCouponCodeDetailFragment newInstance(int orderState) {
        ViewCouponCodeDetailFragment fragment = new ViewCouponCodeDetailFragment();
        Bundle args = new Bundle();
        args.putInt(OrderInfo.ORDER_STATE, orderState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view_couponcode_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {
        Bitmap bitmap = QRCodeUtil.createQRCodeBitmap("123456", ScreenUtil.dp2px(context, 150), ScreenUtil.dp2px(context, 150));
        binding.ivQrcode.setImageBitmap(bitmap);
    }
}