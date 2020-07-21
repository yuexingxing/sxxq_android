package com.sanshao.bs.module.personal.view;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.ActivitySettingNameBinding;
import com.sanshao.bs.module.personal.bean.UserInfo;
import com.sanshao.bs.module.personal.viewmodel.SettingNameViewModel;
import com.sanshao.bs.util.Constants;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

/**
 * 修改名称、昵称
 *
 * @Author yuexingxing
 * @time 2020/7/2
 */
public class SettingNameActivity extends BaseActivity<SettingNameViewModel, ActivitySettingNameBinding> {
    public static final int MODIFY_NAME = 0;
    public static final int MODIFY_NICK_NAME = 1;
    private int optType;

    public static void start(Context context, int optType) {
        Intent starter = new Intent(context, SettingNameActivity.class);
        starter.putExtra(Constants.OPT_TYPE, optType);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_name;
    }

    @Override
    public void initData() {

        optType = getIntent().getIntExtra(Constants.OPT_TYPE, MODIFY_NAME);
        if (MODIFY_NAME == optType) {
            binding.titleBar.setTitle("名称");
            binding.edtName.setHint("请输入用户名称");
            binding.tvTip.setVisibility(View.VISIBLE);
            binding.edtName.setText(SSApplication.getInstance().getUserInfo().name);
        } else {
            binding.titleBar.setTitle("昵称");
            binding.edtName.setHint("请输入用户昵称");
            binding.tvTip.setVisibility(View.GONE);
            binding.edtName.setText(SSApplication.getInstance().getUserInfo().nickName);
        }
        binding.edtName.setSelection(binding.edtName.getText().toString().length());
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
                saveData();
            }
        });
        binding.edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void saveData(){
        UserInfo userInfo = SSApplication.getInstance().getUserInfo();
        if (MODIFY_NAME == optType) {
            userInfo.name = binding.edtName.getText().toString();
        } else {
            userInfo.nickName = binding.edtName.getText().toString();
        }
        SSApplication.getInstance().saveUserInfo(userInfo);
        finish();
    }
}