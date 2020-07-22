package com.sanshao.bs.module.order.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.ContainerUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityAppointmentForConsultationBinding;
import com.sanshao.bs.module.order.bean.ConfirmOrderResponse;
import com.sanshao.bs.module.order.bean.StoreInfo;
import com.sanshao.bs.module.order.model.IConfirmOrderModel;
import com.sanshao.bs.module.order.view.adapter.ConfirmOrderAdapter;
import com.sanshao.bs.module.order.view.dialog.SelectSubscribeTimeDialog;
import com.sanshao.bs.module.order.viewmodel.AppointmentForConsultationViewModel;
import com.sanshao.bs.module.order.viewmodel.ConfirmOrderViewModel;
import com.sanshao.bs.util.OpenLocalMapUtil;
import com.sanshao.bs.util.ToastUtil;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

/**
 * 预约问诊
 *
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class AppointmentForConsultationActivity extends BaseActivity<AppointmentForConsultationViewModel, ActivityAppointmentForConsultationBinding> implements IConfirmOrderModel {

    private ConfirmOrderViewModel mConfirmOrderViewModel;

    public static void start(Context context) {
        Intent starter = new Intent(context, AppointmentForConsultationActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_appointment_for_consultation;
    }

    @Override
    public void initData() {

        mConfirmOrderViewModel = new ConfirmOrderViewModel();
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
        binding.tvCopy.setOnClickListener(v -> {
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText("Label", binding.tvOrderNo.getText().toString());
            cm.setPrimaryClip(mClipData);
            ToastUtil.showShortToast("复制成功");
        });
        binding.rlSubscribeTime.setOnClickListener(v -> {
            SelectSubscribeTimeDialog selectSubscribeTimeDialog = new SelectSubscribeTimeDialog();
            selectSubscribeTimeDialog.setCommonCallBack((postion, object) -> {
                if (object == null) {
                    return;
                }
                binding.tvSubscribeTime.setText((String) object);
            });
            selectSubscribeTimeDialog.showDateDialog(context);
        });
        binding.btnSubscribe.setOnClickListener(v -> {
            OpenLocalMapUtil.openLocalMap(context, OpenLocalMapUtil.START_LATLON[0], OpenLocalMapUtil.START_LATLON[1], OpenLocalMapUtil.SNAME, OpenLocalMapUtil.CITY);
//            finish();
        });
        binding.mulitySetMealView.setOptType(ConfirmOrderAdapter.OPT_TYPE_APPOINTMENT);
        mConfirmOrderViewModel.getOrderInfo(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.bmapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.bmapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.bmapView.onDestroy();
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
        }
    }

    @Override
    public void returnSubmitOrderInfo() {

    }
}