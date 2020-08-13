package com.sanshao.bs.module.login.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.login.bean.GetCodeRequest;
import com.sanshao.bs.module.login.bean.GetCodeResponse;
import com.sanshao.bs.module.login.bean.LoginRequest;
import com.sanshao.bs.module.login.bean.LoginResponse;
import com.sanshao.bs.module.login.bean.ModifyPhoneRequest;
import com.sanshao.bs.module.login.model.ILoginCallBack;
import com.sanshao.bs.module.login.model.LoginModel;
import com.sanshao.bs.util.LoadDialogMgr;
import com.sanshao.bs.util.ToastUtil;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public class LoginViewModel extends ViewModel {
    private String TAG = LoginViewModel.class.getSimpleName();
    private ILoginCallBack mLoginCallBack;

    public interface LoginType {
        String APP_LOGIN = "APP_LOGIN";
        String BIND_PHONE = "BIND_PHONE";
        String CHANGE_PHONE = "CHANGE_PHONE";//修改手机号时发送验证码
    }

    public void setCallBack(ILoginCallBack iLoginCallBack) {
        mLoginCallBack = iLoginCallBack;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "onCleared");
    }

    public void getSMSCode(String mobile, String pinType) {
        LoginModel.getSMSCode(new GetCodeRequest(mobile, pinType), new OnLoadListener<GetCodeResponse>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {
                Log.d(TAG, "onLoadCompleted");
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse<GetCodeResponse> t) {
                ToastUtil.showShortToast(t.getMsg());
                if (mLoginCallBack != null) {
                    mLoginCallBack.onGetCode();
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                Log.d(TAG, "onLoadFailed-" + errMsg);
                ToastUtil.showShortToast(errMsg);
            }
        });
    }

    public void login(String mobile, String code, String referrerMemId) {

        LoginRequest loginRequest = new LoginRequest(mobile, code, referrerMemId);
        LoginModel.login(loginRequest, new OnLoadListener<LoginResponse>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse<LoginResponse> t) {
                ToastUtil.showShortToast(t.getMsg());
                if (mLoginCallBack != null) {
                    mLoginCallBack.onLoginSuccess(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
                if (mLoginCallBack != null) {
                    mLoginCallBack.onLoginFailed();
                }
            }
        });
    }

    public void modifyPhone(String mobile, String code) {

        ModifyPhoneRequest loginRequest = new ModifyPhoneRequest(mobile, code);
        LoginModel.modifyPhone(loginRequest, new OnLoadListener<LoginResponse>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse t) {
                if (mLoginCallBack != null) {
                    mLoginCallBack.onModifyPhone(mobile);
                } else {
                    ToastUtil.showShortToast(t.getMsg());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
            }
        });
    }
}
