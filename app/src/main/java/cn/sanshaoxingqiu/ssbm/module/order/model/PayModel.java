package cn.sanshaoxingqiu.ssbm.module.order.model;

import com.exam.commonbiz.net.BaseObserver;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.ExceptionHandle;
import com.exam.commonbiz.net.OnLoadListener;
import com.exam.commonbiz.net.XApi;
import cn.sanshaoxingqiu.ssbm.module.order.api.PayApiService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PayModel {

    public static void getOrderPayInfo(String salebillId, String payType, final OnLoadListener onLoadListener) {
        XApi.get(PayApiService.class, XApi.HOST_TYPE.NODE)
                .getOrderPayInfo(salebillId, payType)
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

    public static void getOrderStatus(String salebillId, final OnLoadListener onLoadListener) {
        XApi.get(PayApiService.class, XApi.HOST_TYPE.NODE)
                .getOrderStatus(salebillId)
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
