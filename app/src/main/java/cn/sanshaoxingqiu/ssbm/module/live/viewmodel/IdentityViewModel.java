package cn.sanshaoxingqiu.ssbm.module.live.viewmodel;

import androidx.lifecycle.ViewModel;

import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;

import java.util.List;

import cn.sanshaoxingqiu.ssbm.module.invitation.model.InvitationCallBack;
import cn.sanshaoxingqiu.ssbm.module.live.model.IIdentityModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.model.ShoppingCenterModel;
import cn.sanshaoxingqiu.ssbm.util.LoadDialogMgr;

public class IdentityViewModel extends ViewModel {

    private IIdentityModel mCallBack;

    public void setCallBack(IIdentityModel callBack) {
        this.mCallBack = callBack;
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
                if (mCallBack != null) {

                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                if (mCallBack != null) {

                }
            }
        });
    }
}
