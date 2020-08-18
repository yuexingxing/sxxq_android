package com.sanshao.bs.module.personal.myfans.viewmodel;

import android.content.Context;

import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.invitation.bean.UserReferrals;
import com.sanshao.bs.module.invitation.model.InvitationModel;
import com.sanshao.bs.module.personal.myfans.model.IFansCallBack;
import com.sanshao.bs.util.LoadDialogMgr;

import androidx.lifecycle.ViewModel;

public class FansViewModel extends ViewModel {

    private IFansCallBack callBack;

    public void setCallBack(IFansCallBack callBack) {
        this.callBack = callBack;
    }

    public void getFans(Context context) {
        InvitationModel.getUserReferrals(new OnLoadListener<UserReferrals>() {
            @Override
            public void onLoadStart() {
                LoadDialogMgr.getInstance().show(context);
            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse<UserReferrals> t) {
                if(callBack != null){
                    UserReferrals userReferrals = t.getContent();
                    callBack.showFans(userReferrals != null ? userReferrals.referrals : null);
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                if (callBack != null) {
                    callBack.showFans(null);
                }
            }
        });
    }
}
