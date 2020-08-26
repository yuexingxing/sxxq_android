package cn.sanshaoxingqiu.ssbm.module.invitation.model;

import com.exam.commonbiz.net.BaseObserver;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.ExceptionHandle;
import com.exam.commonbiz.net.OnLoadListener;
import com.exam.commonbiz.net.XApi;
import cn.sanshaoxingqiu.ssbm.module.invitation.api.InvitationApiService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InvitationModel {

    public static void getUserReferrals(final OnLoadListener onLoadListener) {
        XApi.get(InvitationApiService.class, XApi.HOST_TYPE.JAVA)
                .requestUserReferrals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver() {

                    @Override
                    public void onStart() {
                        onLoadListener.onLoadStart();
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        onLoadListener.onLoadSucessed(response);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
                        onLoadListener.onLoadFailed(responeThrowable.message);
                    }

                    @Override
                    public void onFinish() {
                        onLoadListener.onLoadCompleted();
                    }

                });
    }

}
