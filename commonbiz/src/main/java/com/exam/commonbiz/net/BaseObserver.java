package com.exam.commonbiz.net;

import android.content.Context;

import com.exam.commonbiz.base.BasicApplication;
import com.google.gson.JsonParseException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    private static final String TAG = "BaseObserver";

    //前台请求
    public BaseObserver() {

    }

    //前台请求，提示框可控
    public BaseObserver(Context context, boolean isProgress) {

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        onStart();
    }

    @Override
    public void onNext(@NonNull BaseResponse<T> response) {
        if (response.getStatus() == 0) {
            onSuccess(response);
        } else if (response.getStatus() == -1) {
            onError(ExceptionHandle.handleException(new JsonParseException("ParseException")));
        } else if (response.getStatus() > 0) {
            response.setDesc(response.getDesc());
            onSuccess(response);
        } else {
            response.setDesc(ErrorUtils.intCode2str(BasicApplication.app, response.getStatus()));
            onSuccess(response);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        ExceptionHandle.ResponeThrowable responeThrowable;
        if (e instanceof Exception) {
            //访问获得对应的Exception
            responeThrowable = ExceptionHandle.handleException(e);
            onError(responeThrowable);
        } else {
            //将Throwable 和 未知错误的status code返回
            responeThrowable = new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN);
            onError(responeThrowable);
        }
        onComplete();
    }

    @Override
    public void onComplete() {
        onFinish();
    }

    public abstract void onStart();

    public abstract void onSuccess(BaseResponse<T> response);

    public abstract void onError(ExceptionHandle.ResponeThrowable responeThrowable);

    public abstract void onFinish();

}
