package cn.sanshaoxingqiu.ssbm.module.register.viewmodel;

import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import cn.sanshaoxingqiu.ssbm.module.login.bean.GetCodeRequest;
import cn.sanshaoxingqiu.ssbm.module.login.bean.GetCodeResponse;
import cn.sanshaoxingqiu.ssbm.module.login.bean.LoginRequest;
import cn.sanshaoxingqiu.ssbm.module.login.bean.LoginResponse;
import cn.sanshaoxingqiu.ssbm.module.login.model.LoginModel;
import cn.sanshaoxingqiu.ssbm.module.personal.bean.UserInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.model.PersonalModel;
import cn.sanshaoxingqiu.ssbm.module.register.model.IRegisterCallBack;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.model.ShoppingCenterModel;
import cn.sanshaoxingqiu.ssbm.util.LoadDialogMgr;
import cn.sanshaoxingqiu.ssbm.util.ToastUtil;

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

    public void getUserInfo() {
        PersonalModel.getUserInfo(new OnLoadListener<UserInfo>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse<UserInfo> t) {
                if (callBack != null){
                    callBack.getUserInfoSucc(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }
}
