package cn.sanshaoxingqiu.ssbm.module.personal.viewmodel;

import android.text.TextUtils;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.exam.commonbiz.bean.UserInfo;

import cn.sanshaoxingqiu.ssbm.module.personal.model.IPersonalCallBack;
import cn.sanshaoxingqiu.ssbm.module.personal.model.PersonalModel;

import com.exam.commonbiz.util.LoadDialogMgr;
import com.exam.commonbiz.util.ToastUtil;

public class PersonalViewModel extends BaseViewModel {

    public IPersonalCallBack mCallBack;

    public void setCallBack(IPersonalCallBack iPersonalCallBack) {
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
                if (mCallBack != null) {
                    UserInfo userInfo = t.getContent();
                    if (userInfo != null && TextUtils.isEmpty(userInfo.nickname)) {
                        userInfo.nickname = userInfo.invitation_code;
                    }
                    mCallBack.returnUserInfo(userInfo);
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                if (mCallBack != null) {
                    mCallBack.returnUserInfo(null);
                }
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
                if (mCallBack != null) {
                    mCallBack.returnUpdateUserInfo(userInfo);
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showShortToast(errMsg);
            }
        });
    }
}