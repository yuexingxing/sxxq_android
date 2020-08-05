package com.sanshao.bs.module.personal.personaldata.view;

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
import com.sanshao.bs.module.personal.event.UpdateUserInfoEvent;
import com.sanshao.bs.module.personal.model.IPersonalCallBack;
import com.sanshao.bs.module.personal.setting.viewmodel.SettingNameViewModel;
import com.sanshao.bs.module.personal.viewmodel.PersonalViewModel;
import com.sanshao.bs.util.ToastUtil;
import com.sanshao.commonui.titlebar.OnTitleBarListener;

import org.greenrobot.eventbus.EventBus;

/**
 * 修改昵称
 *
 * @Author yuexingxing
 * @time 2020/7/2
 */
public class SettingNameActivity extends BaseActivity<PersonalViewModel, ActivitySettingNameBinding> implements IPersonalCallBack {

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingNameActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_name;
    }

    @Override
    public void initData() {

        mViewModel.setCallBack(this);
        binding.titleBar.setTitle("昵称");
        binding.edtName.setHint("请输入用户昵称");
        binding.tvTip.setVisibility(View.GONE);
        binding.edtName.setText(SSApplication.getInstance().getUserInfo().nickname);

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
                UserInfo userInfo = new UserInfo();
                userInfo.nickname = binding.edtName.getText().toString();
                mViewModel.updateUserInfo(userInfo);
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

    @Override
    public void returnUserInfo(UserInfo userInfo) {

    }

    @Override
    public void returnUpdateUserInfo(UserInfo userInfo) {
        ToastUtil.showShortToast("修改成功");
        UserInfo userInfoTemp = SSApplication.getInstance().getUserInfo();
        userInfoTemp.nickname = userInfo.nickname;
        SSApplication.getInstance().saveUserInfo(userInfoTemp);
        EventBus.getDefault().post(new UpdateUserInfoEvent());
        finish();
    }
}