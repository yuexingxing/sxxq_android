package com.sanshao.bs.module.personal.inquiry.view;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.bs.R;
import com.sanshao.bs.databinding.ActivityToBeInquiryDetailBinding;
import com.sanshao.bs.module.order.bean.OrderInfo;
import com.sanshao.bs.module.order.view.ViewCouponCodeActivity;
import com.sanshao.bs.module.personal.inquiry.model.IInquiryModel;
import com.sanshao.bs.module.personal.inquiry.viewmodel.ToBeInquiryDetailViewModel;
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
public class ToBeInquiryDetailActivity extends BaseActivity<BaseViewModel, ActivityToBeInquiryDetailBinding> implements IInquiryModel {

    private ToBeInquiryDetailViewModel mToBeInquiryDetailViewModel;

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

        mToBeInquiryDetailViewModel = new ToBeInquiryDetailViewModel();
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
        binding.rlCallPhone.setOnClickListener(view -> CommandTools.callPhone(context, "123456789"));
        binding.rlViewcode.setOnClickListener(view -> ViewCouponCodeActivity.start(context));
        binding.tvCopy.setOnClickListener(view -> CommandTools.copyToClipboard(context, binding.tvOrderNo.getText().toString()));
        initStoreLocation();
        addStoreMarker();
        mToBeInquiryDetailViewModel.getInquiryDetailInfo();
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

    /**
     * 初始化门店位置
     * 根据后台返回经纬度定位
     */
    private void initStoreLocation() {
        LatLng latLng = new LatLng(31.202845, 121.34672);
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
        binding.bmapView.getMap().setMapStatus(mapStatusUpdate);
    }

    private void addStoreMarker() {
        BaiduMap baiduMap = binding.bmapView.getMap();
        LatLng latLng = baiduMap.getMapStatus().target;
        //准备 marker 的图片
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.image_logo_smal);
        //准备 marker option 添加 marker 使用
        MarkerOptions markerOptions = new MarkerOptions().icon(bitmap).position(latLng);
        //获取添加的 marker 这样便于后续的操作
        Marker marker = (Marker) baiduMap.addOverlay(markerOptions);

        //对 marker 添加点击相应事件
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                // TODO 点击门店图标
                Toast.makeText(getApplicationContext(), "门店图标Marker被点击了！", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.bmapView.onDestroy();
    }

    @Override
    public void onRefreshData(Object object) {

    }

    @Override
    public void onLoadMoreData(Object object) {

    }

    @Override
    public void onNetError() {

    }
}