package com.sanshao.bs.module.order.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.ContainerUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityConfirmOrderBinding;
import com.sanshao.bs.module.order.bean.ConfirmOrderResponse;
import com.sanshao.bs.module.order.bean.StoreInfo;
import com.sanshao.bs.module.order.event.PayStatusChangedEvent;
import com.sanshao.bs.module.order.model.IConfirmOrderModel;
import com.sanshao.bs.module.order.view.adapter.ConfirmOrderAdapter;
import com.sanshao.bs.module.order.viewmodel.ConfirmOrderViewModel;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.util.MathUtil;
import com.sanshao.bs.util.ToastUtil;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 确认订单
 *
 * @Author yuexingxing
 * @time 2020/6/20
 */
public class ConfirmOrderActivity extends BaseActivity<ConfirmOrderViewModel, ActivityConfirmOrderBinding> implements IConfirmOrderModel {

    private double mTotalPrice = 0;
    private int mTotalBuyNum = 0;

    public static void start(Context context) {
        Intent starter = new Intent(context, ConfirmOrderActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void initData() {

        mViewModel.setCallBack(this);
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

        binding.btnConfirm.setOnClickListener(v ->{
            if (mTotalBuyNum <= 0){
                ToastUtil.showShortToast("至少选择一件商品");
                return;
            }

            GoodsDetailInfo goodsDetailInfo = new GoodsDetailInfo();
           mViewModel.submitOrderInfo(goodsDetailInfo);
        });

        binding.mulitySetMealView.setOptType(ConfirmOrderAdapter.OPT_TYPE_CONFIRM_ORDER);
        binding.mulitySetMealView.setOnItemClickListener(new ConfirmOrderAdapter.OnItemClickListener() {
            @Override
            public void onMinusClick(int position, GoodsDetailInfo item) {
                if (item.buyNum > 0) {
                    --item.buyNum;
                    binding.mulitySetMealView.mConfirmOrderAdapter.notifyItemChanged(position);
                    calculatePrice(binding.mulitySetMealView.mConfirmOrderAdapter.getData());
                }
            }

            @Override
            public void onPlusClick(int position, GoodsDetailInfo item) {
                if (item.buyNum >= item.stock){
                    ToastUtil.showShortToast("不能超过库存");
                    return;
                }
                ++item.buyNum;
                binding.mulitySetMealView.mConfirmOrderAdapter.notifyItemChanged(position);
                calculatePrice(binding.mulitySetMealView.mConfirmOrderAdapter.getData());
            }
        });

        mViewModel.getOrderInfo();
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayStatusChangedEvent(PayStatusChangedEvent payStatusChangedEvent) {
        if (payStatusChangedEvent == null){
            return;
        }
        if (payStatusChangedEvent.paySuccess){
            finish();
        }
    }

    @Override
    public void returnConfirmOrder(ConfirmOrderResponse confirmOrderResponse) {
        if (confirmOrderResponse == null){
            return;
        }

        StoreInfo storeInfo = confirmOrderResponse.storeInfo;
        if (storeInfo != null){
            binding.includeStore.tvTel.setText(storeInfo.tel);
            binding.includeStore.tvTime.setText(storeInfo.time);
            binding.includeStore.tvAddress.setText(storeInfo.address);
        }

        if (!ContainerUtil.isEmpty(confirmOrderResponse.goodsTypeDetailInfoList)){
            binding.mulitySetMealView.mConfirmOrderAdapter.addData(confirmOrderResponse.goodsTypeDetailInfoList);
            calculatePrice(confirmOrderResponse.goodsTypeDetailInfoList);
        }

        binding.tvNickName.setText(confirmOrderResponse.nickName);
        binding.tvPhone.setText(confirmOrderResponse.phone);
    }

    @Override
    public void returnSubmitOrderInfo() {
        ConfirmPayActivity.start(context);
    }

    public void calculatePrice(List<GoodsDetailInfo> goodsDetailInfoList){
        if (ContainerUtil.isEmpty(goodsDetailInfoList)){
            return;
        }

        mTotalBuyNum = 0;
        mTotalPrice = 0;
        for (int i = 0; i < goodsDetailInfoList.size(); i++) {
            GoodsDetailInfo goodsDetailInfo = goodsDetailInfoList.get(i);
            mTotalBuyNum += goodsDetailInfo.buyNum;
            mTotalPrice += MathUtil.multiply(goodsDetailInfo.buyNum, goodsDetailInfo.sarti_mkprice);
        }

        String showPrice = MathUtil.getNumExclude0(mTotalPrice) + "元";
        binding.tvBuyNum1.setText(mTotalBuyNum + "件");
        binding.tvBuyNum2.setText("共" + mTotalBuyNum + "件");
        binding.tvTotalPrice1.setText(showPrice);
        binding.tvTotalPrice2.setText(showPrice);
        binding.tvTotalPrice3.setText(showPrice);
    }
}