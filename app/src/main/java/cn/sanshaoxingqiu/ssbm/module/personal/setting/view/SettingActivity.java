package cn.sanshaoxingqiu.ssbm.module.personal.setting.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.ActivitySettingBinding;
import cn.sanshaoxingqiu.ssbm.module.personal.about.AboutUsActivity;
import cn.sanshaoxingqiu.ssbm.module.personal.account.view.AccountSafetyActivity;
import cn.sanshaoxingqiu.ssbm.module.personal.setting.viewmodel.SettingNameViewModel;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

/**
 * 设置
 *
 * @Author yuexingxing
 * @time 2020/7/9
 */
public class SettingActivity extends BaseActivity<SettingNameViewModel, ActivitySettingBinding> {

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
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
        binding.pavAccount.setOnClickListener(v -> AccountSafetyActivity.start(context));
        binding.pavAboutUs.setOnClickListener(v -> AboutUsActivity.start(context));
    }
}