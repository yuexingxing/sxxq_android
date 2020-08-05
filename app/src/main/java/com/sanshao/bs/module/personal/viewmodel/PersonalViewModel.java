package com.sanshao.bs.module.personal.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.personal.bean.UserInfo;
import com.sanshao.bs.module.personal.model.IPersonalCallBack;
import com.sanshao.bs.module.personal.model.PersonalModel;
import com.sanshao.bs.util.LoadDialogMgr;

public class PersonalViewModel extends BaseViewModel {

    public IPersonalCallBack mCallBack;

    public void setCallBack(IPersonalCallBack iPersonalCallBack){
        mCallBack = iPersonalCallBack;
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
                if (mCallBack != null){
                    mCallBack.returnUserInfo(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }

    public void updateUserInfo(UserInfo userInfo) {

        PersonalModel.updateUserInfo(userInfo, new OnLoadListener() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse t) {
                if (mCallBack != null){
                    mCallBack.returnUpdateUserInfo(userInfo);
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }
}