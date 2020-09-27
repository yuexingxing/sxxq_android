package cn.sanshaoxingqiu.ssbm.module.personal.setting.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.exam.commonbiz.util.Constants;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.databinding.ActivitySettingAccountBinding;
import cn.sanshaoxingqiu.ssbm.module.personal.setting.viewmodel.SettingAccountViewModel;

import com.exam.commonbiz.util.ToastUtil;

/**
 * 设置账号、密码
 *
 * @Author yuexingxing
 * @time 2020/7/3
 */
public class SettingAccountActivity extends BaseActivity<SettingAccountViewModel, ActivitySettingAccountBinding> {
    public static final int SETTING_ACCOUNT = 0;
    public static final int SETTING_PASSWORD = 1;
    private int optType;

    public static void start(Context context, int optType) {
        Intent starter = new Intent(context, SettingAccountActivity.class);
        starter.putExtra(Constants.OPT_TYPE, optType);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_account;
    }

    @Override
    public void initData() {

        optType = getIntent().getIntExtra(Constants.OPT_TYPE, 0);
        if (optType == SETTING_ACCOUNT) {
            binding.titleBar.setTitle("设置账号");
        } else {
            binding.titleBar.setTitle("设置密码");
        }
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
        binding.btnSubmit.setOnClickListener(v -> {
            checkAccount();
        });
    }

    private void checkAccount() {
        String psd1 = binding.edtPsd1.getText().toString();
        String psd2 = binding.edtPsd2.getText().toString();
        if (TextUtils.isEmpty(psd1) || psd1.length() < 6) {
            ToastUtil.showShortToast("密码不能为空");
            return;
        } else if (psd1.length() < 6) {
            ToastUtil.showShortToast("密码长度不能少于6位");
            return;
        } else if (TextUtils.equals(psd1, psd2)) {
            ToastUtil.showShortToast("两次密码不一致");
            return;
        }
        finish();
    }

}