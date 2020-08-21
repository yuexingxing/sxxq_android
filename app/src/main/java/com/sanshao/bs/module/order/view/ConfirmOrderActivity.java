package com.sanshao.bs.module.order.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.ContainerUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.ActivityConfirmOrderBinding;
import com.sanshao.bs.module.order.bean.ConfirmOrderResponse;
import com.sanshao.bs.module.order.bean.CreateOrderRequest;
import com.sanshao.bs.module.order.bean.StoreInfo;
import com.sanshao.bs.module.order.event.PayStatusChangedEvent;
import com.sanshao.bs.module.order.model.IConfirmOrderModel;
import com.sanshao.bs.module.order.view.adapter.ConfirmOrderAdapter;
import com.sanshao.bs.module.order.viewmodel.ConfirmOrderViewModel;
import com.sanshao.bs.module.personal.bean.UserInfo;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.model.IGoodsDetailModel;
import com.sanshao.bs.module.shoppingcenter.viewmodel.GoodsDetailViewModel;
import com.sanshao.bs.util.Constants;
import com.sanshao.bs.util.LoadDialogMgr;
import com.sanshao.bs.util.MathUtil;
import com.sanshao.bs.util.ToastUtil;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 确认订单
 *
 * @Author yuexingxing
 * @time 2020/6/20
 */
public class ConfirmOrderActivity extends BaseActivity<ConfirmOrderViewModel, ActivityConfirmOrderBinding> implements IConfirmOrderModel, IGoodsDetailModel {

    private String mSartiId;
    private String mGoodsName;
    private double mTotalPrice = 0;
    private int mTotalBuyNum = 0;
    private int mTotalSharePoint = 0;
    private GoodsDetailViewModel mGoodsDetailViewModel;
    private GoodsDetailInfo mGoodsDetailInfo;

    public static void start(Context context, String sartiId) {
        Intent starter = new Intent(context, ConfirmOrderActivity.class);
        starter.putExtra(Constants.OPT_DATA, sartiId);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void initData() {

        mSartiId = getIntent().getStringExtra(Constants.OPT_DATA);
        mViewModel.setCallBack(this);
        mGoodsDetailViewModel = new GoodsDetailViewModel();
        mGoodsDetailViewModel.setCallBack(this);
        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });

        binding.btnConfirm.setOnClickListener(v -> {
            if (mTotalBuyNum <= 0) {
                ToastUtil.showShortToast("至少选择一件商品");
                return;
            }

            CreateOrderRequest createOrderRequest = new CreateOrderRequest();
            createOrderRequest.note = binding.edtRemark.getText().toString();

            List<CreateOrderRequest.CartInfo> cartInfoList = new ArrayList<>();
            List<GoodsDetailInfo> goodsDetailInfoList = binding.mulitySetMealView.getData();
            for (int i = 0; i < goodsDetailInfoList.size(); i++) {
                GoodsDetailInfo goodsDetailInfo = goodsDetailInfoList.get(i);
                if (goodsDetailInfo.buyNum == 0) {
                    continue;
                }
                CreateOrderRequest.CartInfo cartInfo = new CreateOrderRequest.CartInfo();
                cartInfo.sarti_id = goodsDetailInfo.sarti_id;
                cartInfo.qty = goodsDetailInfo.buyNum;
                cartInfoList.add(cartInfo);
            }
            createOrderRequest.cart = cartInfoList;

            mViewModel.createOrderInfo(createOrderRequest);
        });

        binding.mulitySetMealView.setOptType(ConfirmOrderAdapter.OPT_TYPE_CONFIRM_ORDER);
        binding.mulitySetMealView.setOnItemClickListener(new ConfirmOrderAdapter.OnItemClickListener() {
            @Override
            public void onMinusClick(int position, GoodsDetailInfo item) {
                if (item.buyNum > 1) {
                    --item.buyNum;
                    binding.mulitySetMealView.mConfirmOrderAdapter.notifyItemChanged(position);
                    calculatePrice(binding.mulitySetMealView.mConfirmOrderAdapter.getData());
                }
            }

            @Override
            public void onPlusClick(int position, GoodsDetailInfo item) {
                if (item.buyNum >= 99) {
                    ToastUtil.showShortToast("最多购买99件哦");
                    return;
                }
                ++item.buyNum;
                binding.mulitySetMealView.mConfirmOrderAdapter.notifyItemChanged(position);
                calculatePrice(binding.mulitySetMealView.mConfirmOrderAdapter.getData());
            }
        });

        UserInfo userInfo = SSApplication.getInstance().getUserInfo();
        binding.tvNickName.setText(userInfo.nickname);
        binding.tvPhone.setText(userInfo.mem_phone);
        mGoodsDetailViewModel.getGoodsDetail(context, mSartiId);
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayStatusChangedEvent(PayStatusChangedEvent payStatusChangedEvent) {
        if (payStatusChangedEvent == null) {
            return;
        }
        if (payStatusChangedEvent.paySuccess) {
            finish();
        }
    }

    public void calculatePrice(List<GoodsDetailInfo> goodsDetailInfoList) {
        if (ContainerUtil.isEmpty(goodsDetailInfoList)) {
            return;
        }

        mTotalBuyNum = 0;
        mTotalPrice = 0;
        mTotalSharePoint = 0;
        for (int i = 0; i < goodsDetailInfoList.size(); i++) {
            GoodsDetailInfo goodsDetailInfo = goodsDetailInfoList.get(i);
            mTotalBuyNum += goodsDetailInfo.buyNum;
            mTotalPrice += MathUtil.multiply(goodsDetailInfo.buyNum, goodsDetailInfo.sarti_saleprice);
            mTotalSharePoint += goodsDetailInfo.buyNum * goodsDetailInfo.sarti_point_price;
        }

        String showPrice = MathUtil.getNumExclude0(mTotalPrice) + "元";
        binding.tvBuyNum1.setText(mTotalBuyNum + "件");
        binding.tvBuyNum2.setText("共" + mTotalBuyNum + "件");

        if (mGoodsDetailInfo.isFree()) {
            setTextInfo("免费领取");
        } else if (mGoodsDetailInfo.isPayByPoint()) {
            setTextInfo(mTotalSharePoint + "分享金");
        } else {
            setTextInfo(showPrice);
        }
    }

    private void setTextInfo(String info) {
        binding.tvTotalPrice1.setText(info);
        binding.tvTotalPrice2.setText(info);
        binding.tvTotalPrice3.setText(info);
    }

    @Override
    public void returnCreateOrderInfo(GoodsDetailInfo goodsDetailInfo) {
        if (goodsDetailInfo == null) {
            return;
        }
        goodsDetailInfo.sarti_name = mGoodsName;
        goodsDetailInfo.sarti_saleprice = mGoodsDetailInfo.sarti_saleprice;
        goodsDetailInfo.pay_type = mGoodsDetailInfo.pay_type;
        goodsDetailInfo.sarti_point_price = goodsDetailInfo.sum_point;

        ConfirmPayActivity.start(context, goodsDetailInfo);
    }

    @Override
    public void returnConfirmOrder(ConfirmOrderResponse confirmOrderResponse) {
        if (confirmOrderResponse == null) {
            return;
        }

        StoreInfo storeInfo = confirmOrderResponse.storeInfo;
        if (storeInfo != null) {
            binding.includeStore.tvTel.setText(storeInfo.tel);
            binding.includeStore.tvTime.setText(storeInfo.time);
            binding.includeStore.tvAddress.setText(storeInfo.address);
        }

        if (!ContainerUtil.isEmpty(confirmOrderResponse.goodsTypeDetailInfoList)) {
            binding.mulitySetMealView.mConfirmOrderAdapter.addData(confirmOrderResponse.goodsTypeDetailInfoList);
            calculatePrice(confirmOrderResponse.goodsTypeDetailInfoList);
        }

        binding.tvNickName.setText(confirmOrderResponse.nickName);
        binding.tvPhone.setText(confirmOrderResponse.phone);
    }

    @Override
    public void returnSubmitOrderInfo() {

    }

    @Override
    public void returnGoodsDetail(GoodsDetailInfo goodsDetailInfo) {
        if (goodsDetailInfo == null) {
            return;
        }

        mGoodsDetailInfo = goodsDetailInfo;
        mGoodsName = goodsDetailInfo.sarti_name;
        List<GoodsDetailInfo> goodsDetailInfoList = new ArrayList<>();
        goodsDetailInfoList.add(goodsDetailInfo);
        binding.mulitySetMealView.setData(goodsDetailInfoList);
        calculatePrice(goodsDetailInfoList);
    }
}