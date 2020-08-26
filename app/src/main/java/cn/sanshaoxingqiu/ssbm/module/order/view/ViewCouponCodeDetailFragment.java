package cn.sanshaoxingqiu.ssbm.module.order.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.exam.commonbiz.base.BaseFragment;
import com.exam.commonbiz.util.QRCodeUtil;
import com.exam.commonbiz.util.ScreenUtil;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.FragmentViewCouponcodeDetailBinding;
import cn.sanshaoxingqiu.ssbm.module.order.viewmodel.OrderStatusViewModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.util.Constants;

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
        if (writeOffInfo.canUse()) {
            binding.ivQrcodeUsed.setVisibility(View.GONE);
            binding.ivQrcode.setVisibility(View.VISIBLE);
            String qrData = String.format("salebill_id=%s&code=%s", writeOffInfo.salebillId, writeOffInfo.code);
            int qrcodeHeight = ScreenUtil.dp2px(context, 150);
            Bitmap bitmap = QRCodeUtil.createQRCodeBitmap(qrData, qrcodeHeight, qrcodeHeight);
            binding.ivQrcode.setImageBitmap(bitmap);
        } else {
            binding.ivQrcodeUsed.setVisibility(View.VISIBLE);
            binding.ivQrcode.setVisibility(View.GONE);
        }

    }
}