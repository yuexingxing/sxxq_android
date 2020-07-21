package com.sanshao.bs.module.login.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.login.bean.LoginBean;
import com.sanshao.bs.module.login.model.LoginModel;
import com.sanshao.bs.util.LoadDialogMgr;
import com.sanshao.bs.util.ToastUtil;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public class LoginViewModel extends ViewModel {
    private String TAG = LoginViewModel.class.getSimpleName();

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "onCleared");
    }

    public void getSMSCode(String mobile, String tpye) {
        LoginModel.getSMSCode(mobile, tpye, new OnLoadListener() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {
                Log.d(TAG, "onLoadCompleted");
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse t) {
                Log.d(TAG, "onLoadSucessed");
//                getV().onGetSMSCode();
            }

            @Override
            public void onLoadFailed(String errMsg) {
//                getV().onGetSMSCode();
                ToastUtil.showShortToast(errMsg);
            }
        });
    }

    public void login(String mobile, String password) {
        LoginModel.login(mobile, password, new OnLoadListener<LoginBean>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse<LoginBean> t) {
//                getV().onLoginResponse(t.getData());
            }

            @Override
            public void onLoadFailed(String errMsg) {
//                getV().onLoginResponse(null);
            }
        });
    }
}
