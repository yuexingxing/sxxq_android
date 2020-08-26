package cn.sanshaoxingqiu.ssbm.module.personal.model;

import com.exam.commonbiz.net.BaseObserver;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.ExceptionHandle;
import com.exam.commonbiz.net.OnLoadListener;
import com.exam.commonbiz.net.XApi;

import cn.sanshaoxingqiu.ssbm.module.personal.bean.UserInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.personaldata.api.PersonalApiService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author yuexingxing
 * @time 2020/7/22
 */
public class PersonalModel {

    public static void getUserInfo(final OnLoadListener onLoadListener) {
        XApi.get(PersonalApiService.class, XApi.HOST_TYPE.NODE)
                .getUserInfo()
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

    public static void updateUserInfo(UserInfo userInfo, final OnLoadListener onLoadListener) {
        XApi.get(PersonalApiService.class, XApi.HOST_TYPE.NODE)
                .updateUserInfo(userInfo)
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
