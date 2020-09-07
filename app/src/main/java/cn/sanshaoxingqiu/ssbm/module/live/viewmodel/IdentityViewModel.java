package cn.sanshaoxingqiu.ssbm.module.live.viewmodel;

import androidx.lifecycle.ViewModel;

import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;

import cn.sanshaoxingqiu.ssbm.module.live.api.LiveApplyRequest;
import cn.sanshaoxingqiu.ssbm.module.live.bean.LiveApplyResponse;
import cn.sanshaoxingqiu.ssbm.module.live.model.IIdentityModel;
import cn.sanshaoxingqiu.ssbm.module.live.model.IdentityModel;
import com.exam.commonbiz.util.LoadDialogMgr;
import com.exam.commonbiz.util.ToastUtil;

public class IdentityViewModel extends ViewModel {

    private IIdentityModel mCallBack;

    public void setCallBack(IIdentityModel callBack) {
        this.mCallBack = callBack;
    }

    public void liveApply(LiveApplyRequest liveApplyRequest) {
        IdentityModel.liveApply(liveApplyRequest, new OnLoadListener() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse t) {
                if (mCallBack != null && t.isOk()) {
                    mCallBack.returnLiveApply();
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showLongToast(errMsg);
            }
        });
    }

    public void getAnchorDetail() {
        IdentityModel.getAnchorDetail(new OnLoadListener<LiveApplyResponse>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse<LiveApplyResponse> t) {
                if (mCallBack != null) {
                    mCallBack.returnAnchorDetail(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                ToastUtil.showLongToast(errMsg);
            }
        });
    }
}
