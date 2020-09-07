package com.sanshao.livemodule.zhibo.live;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.livemodule.R;
import com.sanshao.livemodule.databinding.ActivityStartLiveBinding;

/**
 * 开播设置
 *
 * @Author yuexingxing
 * @time 2020/8/31
 */
public class StartLiveActivity extends BaseActivity<BaseViewModel, ActivityStartLiveBinding> {

    public static void start(Context context) {
        Intent starter = new Intent(context, StartLiveActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start_live;
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
    }


}