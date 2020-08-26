package cn.sanshaoxingqiu.ssbm.module.splash;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import cn.sanshaoxingqiu.ssbm.util.LoadDialogMgr;

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
                    callBack.returnSplashInfo(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {

            }
        });
    }
}
