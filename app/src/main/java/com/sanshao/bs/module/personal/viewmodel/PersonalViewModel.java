package com.sanshao.bs.module.personal.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.personal.bean.UserInfo;
import com.sanshao.bs.module.personal.model.IPersonalCallBack;
import com.sanshao.bs.module.personal.model.PersonalModel;
import com.sanshao.bs.util.LoadDialogMgr;

public class PersonalViewModel extends BaseViewModel {

    public void getUserInfo(IPersonalCallBack callBack) {

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
                    callBack.returnUserInfo(t.getData());
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

            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }
}