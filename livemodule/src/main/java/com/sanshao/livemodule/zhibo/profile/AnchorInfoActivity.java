package com.sanshao.livemodule.zhibo.profile;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.base.BasicApplication;
import com.exam.commonbiz.bean.UserInfo;
import com.exam.commonbiz.util.GlideUtil;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.livemodule.R;
import com.sanshao.livemodule.databinding.ActivityAnchorInfoBinding;

/**
 * 主播主页
 *
 * @Author yuexingxing
 * @time 2020/8/31
 */
public class AnchorInfoActivity extends BaseActivity<BaseViewModel, ActivityAnchorInfoBinding> {

    private UserInfo mUserInfo;

    public static void start(Context context) {
        Intent starter = new Intent(context, AnchorInfoActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initData() {

        binding.titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View view) {
                finish();
            }

            @Override
            public void onTitleClick(View view) {

            }

            @Override
            public void onRightClick(View view) {

            }
        });

        mUserInfo = BasicApplication.app.getUserInfo();
        GlideUtil.loadImage(mUserInfo.avatar, binding.ivAvatar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_anchor_info;
    }
}