package com.sanshao.bs.module.personal.inquiry.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityToBeInquiryDetailBinding;
import com.sanshao.bs.module.order.view.ViewCouponCodeActivity;
import com.sanshao.bs.util.CommandTools;
import com.sanshao.bs.util.OpenLocalMapUtil;
import com.sanshao.commonui.dialog.CommonBottomDialog;
import com.sanshao.commonui.dialog.CommonDialogInfo;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 问诊详情
 *
 * @Author yuexingxing
 * @time 2020/7/24
 */
public class ToBeInquiryDetailActivity extends BaseActivity<BaseViewModel, ActivityToBeInquiryDetailBinding> {

    public static void start(Context context) {
        Intent starter = new Intent(context, ToBeInquiryDetailActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_to_be_inquiry_detail;
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

        binding.bmapView.showZoomControls(false);//隐藏缩放控件
        binding.bmapView.showScaleControl(false);//隐藏比例尺
        View child = binding.bmapView.getChildAt(1);//隐藏logo
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }

        BaiduMap baiduMap = binding.bmapView.getMap();
        UiSettings settings = baiduMap.getUiSettings();
        settings.setAllGesturesEnabled(false);

        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                OpenLocalMapUtil.openLocalMap(context, OpenLocalMapUtil.START_LATLON[0], OpenLocalMapUtil.START_LATLON[1], OpenLocalMapUtil.SNAME, OpenLocalMapUtil.CITY);
            }

            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
                OpenLocalMapUtil.openLocalMap(context, OpenLocalMapUtil.START_LATLON[0], OpenLocalMapUtil.START_LATLON[1], OpenLocalMapUtil.SNAME, OpenLocalMapUtil.CITY);
            }
        });
        binding.tvCancel.setOnClickListener(view -> {

            List<CommonDialogInfo> commonDialogInfoList = new ArrayList<>();
            commonDialogInfoList.add(new CommonDialogInfo("确认取消"));
            commonDialogInfoList.add(new CommonDialogInfo("我再想想"));

            new CommonBottomDialog()
                    .init(context)
                    .setData(commonDialogInfoList)
                    .setOnItemClickListener(commonDialogInfo -> {

                    }).withBottomButton(View.GONE)
                    .show();
        });
        binding.rlCallPhone.setOnClickListener(view -> {
            CommandTools.callPhone(context, "123456789");
        });
        binding.rlViewcode.setOnClickListener(view -> {
            ViewCouponCodeActivity.start(context);
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