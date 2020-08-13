package com.sanshao.bs.module.register.viewmodel;

import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.login.bean.GetCodeRequest;
import com.sanshao.bs.module.login.bean.GetCodeResponse;
import com.sanshao.bs.module.login.bean.LoginRequest;
import com.sanshao.bs.module.login.bean.LoginResponse;
import com.sanshao.bs.module.login.model.LoginModel;
import com.sanshao.bs.module.register.model.IRegisterCallBack;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.model.ShoppingCenterModel;
import com.sanshao.bs.util.LoadDialogMgr;
import com.sanshao.bs.util.ToastUtil;

import java.util.List;

import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {

    private IRegisterCallBack callBack;

    public void setCallBack(IRegisterCallBack callBack) {
        this.callBack = callBack;
    }

    public void getGoodsList(String artiTagId) {
        ShoppingCenterModel.getGoodsList(artiTagId, 0, 10, new OnLoadListener<List<GoodsDetailInfo>>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<List<GoodsDetailInfo>> t) {
                if (callBack != null) {
                    callBack.showGoods(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                if (callBack != null) {
                    callBack.showGoods(null);
                }
            }
        });
    }

    public void getSMSCode(String mobile, String pinType) {
        LoginModel.getSMSCode(new GetCodeRequest(mobile, pinType), new OnLoadListener<GetCodeResponse>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse<GetCodeResponse> t) {
                ToastUtil.showShortToast(t.getMsg());
                if (callBack != null) {
                    callBack.onGetCode();
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
            }
        });
    }

    public void reister(String mobile, String code, String referrerMemId) {

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
                if (callBack != null) {
                    callBack.registerSucc(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
                if (callBack != null) {
                    callBack.registerFail();
                }
            }
        });
    }
}
