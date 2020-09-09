package com.sanshao.livemodule.zhibo.live;

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
import com.sanshao.livemodule.liveroom.MLVBLiveRoomImpl;
import com.sanshao.livemodule.zhibo.login.TCUserMgr;

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
    protected int getLayoutId() {
        return R.layout.activity_anchor_info;
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
        binding.tvStartLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartLiveActivity.start(context);
            }
        });

        mUserInfo = BasicApplication.app.getUserInfo();
        binding.tvName.setText(mUserInfo.nickname);
        GlideUtil.loadImage(mUserInfo.avatar, binding.ivAvatar);
    }

    @Override
    protected boolean isUseFullScreenMode() {
        return true;
    }

    @Override
    protected boolean isUseBlackFontWithStatusBar() {
        return true;
    }

    @Override
    public int getStatusBarColor() {
        return R.color.transparent;
    }

}