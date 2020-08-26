package cn.sanshaoxingqiu.ssbm.module.login.model;

import com.exam.commonbiz.net.XApi;
import cn.sanshaoxingqiu.ssbm.module.login.BaseApiService;
import com.exam.commonbiz.net.BaseObserver;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.ExceptionHandle;
import com.exam.commonbiz.net.OnLoadListener;
import cn.sanshaoxingqiu.ssbm.module.login.bean.GetCodeRequest;
import cn.sanshaoxingqiu.ssbm.module.login.bean.LoginRequest;
import cn.sanshaoxingqiu.ssbm.module.login.bean.ModifyPhoneRequest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public class LoginModel {

    public static void getSMSCode(GetCodeRequest getCodeRequest, final OnLoadListener onLoadListener) {
        XApi.get(BaseApiService.class, XApi.HOST_TYPE.NODE)
                .getSMSCode(getCodeRequest)
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

    public static void login(LoginRequest loginRequest, final OnLoadListener onLoadListener) {
        XApi.get(BaseApiService.class, XApi.HOST_TYPE.NODE)
                .login(loginRequest)
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

    public static void modifyPhone(ModifyPhoneRequest modifyPhoneRequest, final OnLoadListener onLoadListener) {
        XApi.get(BaseApiService.class, XApi.HOST_TYPE.NODE)
                .modifyPhone(modifyPhoneRequest)
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
