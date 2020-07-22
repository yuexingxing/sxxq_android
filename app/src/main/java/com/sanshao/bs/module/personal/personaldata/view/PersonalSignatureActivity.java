package com.sanshao.bs.module.personal.personaldata.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.View;

import com.exam.commonbiz.base.BaseActivity;
import com.sanshao.commonui.titlebar.OnTitleBarListener;
import com.sanshao.bs.R;
import com.sanshao.bs.SSApplication;
import com.sanshao.bs.databinding.ActivityPersonalSignatureBinding;
import com.sanshao.bs.module.personal.bean.UserInfo;
import com.sanshao.bs.module.personal.personaldata.viewmodel.PersonalSignatureViewModel;

/**
 * 个性签名
 *
 * @Author yuexingxing
 * @time 2020/7/2
 */
public class PersonalSignatureActivity extends BaseActivity<PersonalSignatureViewModel, ActivityPersonalSignatureBinding> {

    private PersonalSignatureViewModel mPersonalSignatureViewModel;

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

        mPersonalSignatureViewModel = new PersonalSignatureViewModel();
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

        //TODO 更新签名
//        UserInfo userInfo = new UserInfo();
//        userInfo.signature = binding.edtContent.getText().toString();
//        mPersonalSignatureViewModel.updateUserInfo(userInfo);

        UserInfo userInfo = SSApplication.getInstance().getUserInfo();
        userInfo.signature = binding.edtContent.getText().toString();
        SSApplication.getInstance().saveUserInfo(userInfo);
        finish();
    }
}