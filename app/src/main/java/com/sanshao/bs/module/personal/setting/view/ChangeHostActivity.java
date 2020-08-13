package com.sanshao.bs.module.personal.setting.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.cache.ACache;
import com.exam.commonbiz.config.ConfigSP;
import com.exam.commonbiz.net.HostUrl;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.ActivityChangeHostBinding;
import com.sanshao.bs.module.login.view.LoginActivity;
import com.sanshao.bs.module.personal.bean.UserInfo;
import com.sanshao.bs.util.RestartAPPTool;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

/**
 * 切换环境
 *
 * @Author yuexingxing
 * @time 2020/7/23
 */
public class ChangeHostActivity extends BaseActivity<BaseViewModel, ActivityChangeHostBinding> {

    private ConfigSP.HOST_TYPE mCurrentIndex;

    public static void start(Context context) {
        Intent starter = new Intent(context, ChangeHostActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_host;
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
        binding.rbDev.setText(String.format("DEV\n%s\n%s", HostUrl.DEV.JAVA, HostUrl.DEV.NODE));
        binding.rbPre.setText(String.format("PRE\n%s\n%s", HostUrl.PRE.JAVA, HostUrl.PRE.NODE));
        binding.rbPro.setText(String.format("PRO\n%s\n%s", HostUrl.PRO.JAVA, HostUrl.PRO.NODE));

        binding.btnOk.setOnClickListener(view -> {
            ConfigSP.HOST_TYPE type;
            if (binding.rbDev.isChecked()) {
                type = ConfigSP.HOST_TYPE.DEV;
            } else if (binding.rbPre.isChecked()) {
                type = ConfigSP.HOST_TYPE.PRE;
            } else {
                type = ConfigSP.HOST_TYPE.PRO;
            }
            if (type == mCurrentIndex) {
                finish();
                return;
            }
            ACache.get(context).put(ConfigSP.SP_TOKEN, "");
            ACache.get(context).put(ConfigSP.SP_CURRENT_HOST, type);
            RestartAPPTool.restartAPP(getApplicationContext(), 100);
        });

        if (ACache.get(context).getAsObject(ConfigSP.SP_CURRENT_HOST) != null) {
            mCurrentIndex = (ConfigSP.HOST_TYPE) ACache.get(context).getAsObject(ConfigSP.SP_CURRENT_HOST);
            if (mCurrentIndex == ConfigSP.HOST_TYPE.DEV) {
                binding.rbDev.setChecked(true);
            } else if (mCurrentIndex == ConfigSP.HOST_TYPE.PRE) {
                binding.rbPre.setChecked(true);
            } else {
                binding.rbPro.setChecked(true);
            }
        }
    }
}