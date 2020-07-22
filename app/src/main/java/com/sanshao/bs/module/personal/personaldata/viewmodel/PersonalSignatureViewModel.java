package com.sanshao.bs.module.personal.personaldata.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.personal.bean.UserInfo;
import com.sanshao.bs.module.personal.personaldata.model.PersonalModel;
import com.sanshao.bs.util.LoadDialogMgr;

public class PersonalSignatureViewModel extends BaseViewModel {

    public void getUserInfo() {

        PersonalModel.getUserInfo(new OnLoadListener() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse t) {

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

            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }
}