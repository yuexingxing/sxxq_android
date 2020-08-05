package com.sanshao.bs.module.personal.personaldata.view;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.bs.module.personal.event.UpdateUserInfoEvent;
import com.sanshao.bs.module.personal.model.IPersonalCallBack;
import com.sanshao.bs.module.personal.viewmodel.PersonalViewModel;
import com.sanshao.bs.util.ToastUtil;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.ActivityPersonalSignatureBinding;
import com.sanshao.bs.module.personal.bean.UserInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * 个性签名
 *
 * @Author yuexingxing
 * @time 2020/7/2
 */
public class PersonalSignatureActivity extends BaseActivity<PersonalViewModel, ActivityPersonalSignatureBinding> implements IPersonalCallBack {

    public static void start(Context context) {
        Intent starter = new Intent(context, PersonalSignatureActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_signature;
    }

    @Override
    public void initData() {

        mViewModel.setCallBack(this);
        String content = SSApplication.getInstance().getUserInfo().signature;
        if (TextUtils.isEmpty(content)) {
            content = "";
        }

        binding.edtContent.setText(content);
        binding.edtContent.setSelection(content.length());
        binding.tvLimit.setText(content.length() + "/" + 30);
        setEditTipIsVisible();
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
                submit();
            }
        });
        binding.edtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEditTipIsVisible();
                binding.tvLimit.setText(s.length() + "/" + 30);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.edtContent.setOnFocusChangeListener((view, b) -> {
            setEditTipIsVisible();
        });
    }

    private void setEditTipIsVisible() {
        if (TextUtils.isEmpty(binding.edtContent.getText().toString())) {
            binding.llEditTip.setVisibility(View.VISIBLE);
        } else {
            binding.llEditTip.setVisibility(View.GONE);
        }
    }

    /**
     * 提交信息
     */
    private void submit() {

        UserInfo userInfo = new UserInfo();
        userInfo.signature = binding.edtContent.getText().toString();
        mViewModel.updateUserInfo(userInfo);
    }

    @Override
    public void returnUserInfo(UserInfo userInfo) {

    }

    @Override
    public void returnUpdateUserInfo(UserInfo userInfo) {
        ToastUtil.showShortToast("修改成功");
        UserInfo userInfoTemp = SSApplication.getInstance().getUserInfo();
        userInfoTemp.signature = userInfo.signature;
        SSApplication.getInstance().saveUserInfo(userInfoTemp);
        EventBus.getDefault().post(new UpdateUserInfoEvent());
        finish();
    }
}