package com.sanshao.bs.module.invitation.viewmodel;

import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.module.invitation.bean.UserReferrals;
import com.sanshao.bs.module.invitation.model.InvitationCallBack;
import com.sanshao.bs.module.invitation.model.InvitationModel;
import com.sanshao.bs.module.shoppingcenter.bean.GoodsDetailInfo;
import com.sanshao.bs.module.shoppingcenter.model.ShoppingCenterModel;
import com.sanshao.bs.util.LoadDialogMgr;

import java.util.List;

import androidx.lifecycle.ViewModel;

public class InvitationViewModel extends ViewModel {

    private InvitationCallBack callBack;

    public void setCallBack(InvitationCallBack callBack) {
        this.callBack = callBack;
    }

    public void getGoodsList(String artiTagId) {
        ShoppingCenterModel.getGoodsList(artiTagId, 0, 10, new OnLoadListener<List<GoodsDetailInfo>>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
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

    public void getUserReferrals() {
        InvitationModel.getUserReferrals(new OnLoadListener<UserReferrals>() {
            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse<UserReferrals> t) {
                if(callBack == null){
                    callBack.showUserReferrals(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }
}
