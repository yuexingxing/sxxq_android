package cn.sanshaoxingqiu.ssbm.module.personal.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import cn.sanshaoxingqiu.ssbm.module.personal.bean.UserInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.model.IPersonalCallBack;
import cn.sanshaoxingqiu.ssbm.module.personal.model.PersonalModel;
import cn.sanshaoxingqiu.ssbm.util.LoadDialogMgr;
import cn.sanshaoxingqiu.ssbm.util.ToastUtil;

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
                if (mCallBack != null){
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
                if (mCallBack != null){
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