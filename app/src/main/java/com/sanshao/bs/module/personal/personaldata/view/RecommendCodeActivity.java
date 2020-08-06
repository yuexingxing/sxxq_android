package com.sanshao.bs.module.personal.personaldata.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.CommonCallBack;
import com.exam.commonbiz.util.FileUtil;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.ActivityRecommendCodeBinding;
import com.sanshao.bs.module.personal.bean.UserInfo;
import com.sanshao.bs.module.personal.setting.viewmodel.RecommendCodeViewModel;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.view.dialog.GoodsPosterDialog;
import com.sanshao.bs.util.BitmapUtil;
import com.sanshao.bs.util.GlideUtil;
import com.sanshao.bs.util.ShareUtils;
import com.sanshao.bs.util.ToastUtil;
import com.sanshao.commonui.dialog.CommonBottomDialog;
import com.sanshao.commonui.dialog.CommonDialogInfo;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.commonutil.permission.PermissionGroup;
import com.sanshao.commonutil.permission.RxPermissions;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的推荐码
 *
 * @Author yuexingxing
 * @time 2020/7/2
 */
public class RecommendCodeActivity extends BaseActivity<RecommendCodeViewModel, ActivityRecommendCodeBinding> {

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
                        Bitmap bitmap = BitmapUtil.viewConversionBitmap(binding.ivQrcode);
                        boolean saveSuccess = FileUtil.saveImageToGallery(context, bitmap, picName);
                        if (saveSuccess) {
                            ToastUtil.showShortToast("图片保存成功");
                        }
                    });
        });
        binding.llShare.setOnClickListener(v -> {
            share();
        });

        UserInfo userInfo = SSApplication.getInstance().getUserInfo();
        binding.tvName.setText(userInfo.nickname);
        binding.tvInviteCode.setText("我的推荐码：" + userInfo.invitation_code);
        GlideUtil.loadImage(userInfo.invitation_weapp_url, binding.ivQrcode);
        GlideUtil.loadImage(userInfo.avatar, binding.ivAvatar, R.drawable.image_placeholder_two);
    }

    private void share() {

        List<CommonDialogInfo> commonDialogInfoList = new ArrayList<>();
        commonDialogInfoList.add(new CommonDialogInfo("分享到微信"));
        commonDialogInfoList.add(new CommonDialogInfo("生成海报"));

        new CommonBottomDialog()
                .init(this)
                .setData(commonDialogInfoList)
                .setOnItemClickListener(commonDialogInfo -> {
                    if (commonDialogInfo.position == 0) {
                        ShareUtils.shareText(RecommendCodeActivity.this, "title", SHARE_MEDIA.WEIXIN, new CommonCallBack() {
                            @Override
                            public void callback(int postion, Object object) {

                            }
                        });
                    } else {
                        new GoodsPosterDialog().show(context, new GoodsDetailInfo());
                    }
                })
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}