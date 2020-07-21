package com.sanshao.bs.module.personal.personaldata.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.cache.ACache;
import com.exam.commonbiz.config.ConfigSP;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.FileUtil;
import com.exam.commonbiz.util.QRCodeUtil;
import com.exam.commonbiz.util.ScreenUtil;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.commonutil.permission.PermissionGroup;
import com.sanshao.commonutil.permission.RxPermissions;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.ActivityRecommendCodeBinding;
import com.sanshao.bs.module.personal.setting.viewmodel.RecommendCodeViewModel;
import com.sanshao.bs.module.shoppingcenter.view.dialog.GoodsDetailShareDialog;
import com.sanshao.bs.util.ToastUtil;

/**
 * 我的推荐码
 *
 * @Author yuexingxing
 * @time 2020/7/2
 */
public class RecommendCodeActivity extends BaseActivity<RecommendCodeViewModel, ActivityRecommendCodeBinding> {

    private Bitmap mBitmap;

    public static void start(Context context) {
        Intent starter = new Intent(context, RecommendCodeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recommend_code;
    }

    @SuppressLint("CheckResult")
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
        binding.llSave.setOnClickListener(v -> {
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.request(PermissionGroup.STORAGE)
                    .subscribe(aBoolean -> {
                        if (!aBoolean) {
                            ToastUtil.showShortToast("无权限");
                            return;
                        }
                        String picName = System.currentTimeMillis() + ".png";
                        boolean saveSuccess = FileUtil.saveImageToGallery(context, mBitmap, picName);
                        if (saveSuccess) {
                            ToastUtil.showShortToast("图片保存成功");
                        }
                    });
        });
        binding.llShare.setOnClickListener(v -> {
            new GoodsDetailShareDialog().show(context, new CommonCallBack() {
                @Override
                public void callback(int postion, Object object) {

                }
            });
        });

        String content = "www.baidu.com";
        mBitmap = QRCodeUtil.createQRCodeBitmap(content, ScreenUtil.dp2px(context, 278), ScreenUtil.dp2px(context, 278));
        binding.ivQrcode.setImageBitmap(mBitmap);

        Bitmap bitmap = ACache.get(context).getAsBitmap(ConfigSP.UserInfo.AVATAR);
        binding.ivAvatar.setImageBitmap(bitmap);
        binding.tvName.setText(SSApplication.getInstance().getUserInfo().name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }
}