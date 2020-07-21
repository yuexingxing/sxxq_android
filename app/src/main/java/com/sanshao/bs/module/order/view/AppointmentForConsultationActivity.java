package com.sanshao.bs.module.order.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.bs.module.order.bean.OrderInfo;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityAppointmentForConsultationBinding;
import com.sanshao.bs.module.order.view.adapter.AppointmentForConsultationAdapter;
import com.sanshao.bs.module.order.view.dialog.SelectSubscribeTimeDialog;
import com.sanshao.bs.module.order.viewmodel.AppointmentForConsultationViewModel;
import com.sanshao.bs.util.ToastUtil;

/**
 * 预约问诊
 *
 * @Author yuexingxing
 * @time 2020/7/7
 */
public class AppointmentForConsultationActivity extends BaseActivity<AppointmentForConsultationViewModel, ActivityAppointmentForConsultationBinding> {

    private AppointmentForConsultationAdapter mAppointmentForConsultationAdapter;

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
            finish();
        });
        binding.rlOpen.setOnClickListener(v -> {
            binding.rlOpen.setVisibility(View.GONE);
            binding.llContent.setVisibility(View.VISIBLE);
        });
        binding.tvClose.setOnClickListener(v -> {
            binding.llContent.setVisibility(View.GONE);
            binding.rlOpen.setVisibility(View.VISIBLE);
        });

        mAppointmentForConsultationAdapter = new AppointmentForConsultationAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.setAdapter(mAppointmentForConsultationAdapter);
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.setFocusable(false);

        for (int i = 0; i < 2; i++) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.name = "玻尿酸美容护肤不二之选，还你天使容颜，变美不容错误。";
            mAppointmentForConsultationAdapter.addData(orderInfo);
        }
    }

}