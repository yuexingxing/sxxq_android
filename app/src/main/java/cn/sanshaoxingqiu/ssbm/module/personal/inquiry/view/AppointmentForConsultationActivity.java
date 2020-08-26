package cn.sanshaoxingqiu.ssbm.module.personal.inquiry.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.ActivityAppointmentForConsultationBinding;
import cn.sanshaoxingqiu.ssbm.module.order.bean.ConfirmOrderResponse;
import cn.sanshaoxingqiu.ssbm.module.order.bean.StoreInfo;
import cn.sanshaoxingqiu.ssbm.module.order.model.IConfirmOrderModel;
import cn.sanshaoxingqiu.ssbm.module.order.view.adapter.ConfirmOrderAdapter;
import cn.sanshaoxingqiu.ssbm.module.order.viewmodel.ConfirmOrderViewModel;
import cn.sanshaoxingqiu.ssbm.module.personal.personaldata.dialog.SelectBirthdayDialog;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.util.CommandTools;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.ContainerUtil;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

/**
 * 预约问诊
 *
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class AppointmentForConsultationActivity extends BaseActivity<ConfirmOrderViewModel, ActivityAppointmentForConsultationBinding> implements IConfirmOrderModel {

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

        mViewModel.setCallBack(this);
        binding.mulitySetMealView.setTitleVisible(View.GONE);
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
        binding.tvCopy.setOnClickListener(v -> CommandTools.copyToClipboard(context, binding.tvOrderNo.getText().toString()));
        binding.rlSubscribeTime.setOnClickListener(v -> {
            boolean[] timeType = new boolean[]{true, true, true, true, true, false};
            new SelectBirthdayDialog()
                    .init(context, "预约时间", timeType)
                    .setCommonCallBack((postion, object) -> {
                        if (object == null) {
                            return;
                        }
                        binding.tvSubscribeTime.setText((String) object);
                    }).show();
        });
        binding.btnSubscribe.setOnClickListener(v -> {
            finish();
        });
        binding.mulitySetMealView.setOptType(ConfirmOrderAdapter.OPT_TYPE_APPOINTMENT);
        mViewModel.getOrderInfo();
    }

    @Override
    public void returnCreateOrderInfo(GoodsDetailInfo goodsDetailInfo) {

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