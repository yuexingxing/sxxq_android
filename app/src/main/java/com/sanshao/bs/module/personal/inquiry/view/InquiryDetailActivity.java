package com.sanshao.bs.module.personal.inquiry.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityInquiryDetailBinding;
import com.sanshao.bs.util.OpenLocalMapUtil;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

/**
 * 问诊详情
 *
 * @Author yuexingxing
 * @time 2020/7/24
 */
public class InquiryDetailActivity extends BaseActivity<BaseViewModel, ActivityInquiryDetailBinding> {

    public static void start(Context context) {
        Intent starter = new Intent(context, InquiryDetailActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inquiry_detail;
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
        OpenLocalMapUtil.openLocalMap(context, OpenLocalMapUtil.START_LATLON[0], OpenLocalMapUtil.START_LATLON[1], OpenLocalMapUtil.SNAME, OpenLocalMapUtil.CITY);

        binding.tvCancel.setOnClickListener(view -> {

        });
        binding.rlCallPhone.setOnClickListener(view -> {

        });
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
}