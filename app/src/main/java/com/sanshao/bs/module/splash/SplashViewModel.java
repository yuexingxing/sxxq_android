package com.sanshao.bs.module.splash;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import com.sanshao.bs.util.LoadDialogMgr;

public class SplashViewModel extends BaseViewModel {

    public void getSplashInfo(String type, ISplashCallBack callBack) {

        SplashModel.getSplashInfo(type, new OnLoadListener<SplashInfo>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {
                LoadDialogMgr.getInstance().dismiss();
            }

            @Override
            public void onLoadSucessed(BaseResponse<SplashInfo> t) {
                if (callBack != null) {
                    callBack.returnSplashInfo(t.getData());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }
}
