package com.exam.commonbiz.net;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.exam.commonbiz.event.IdentityExpiredEvent;
import com.google.gson.JsonParseException;

import org.greenrobot.eventbus.EventBus;

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
        if (TextUtils.equals(response.getRet(), "OK")) {
            onSuccess(response);
        } else if (TextUtils.equals(response.getRet(), "ERROR")) {
            ExceptionHandle.ResponeThrowable responeThrowable = new ExceptionHandle.ResponeThrowable(null, ExceptionHandle.ERROR.SERVICE_ERROR);
            responeThrowable.code = ExceptionHandle.ERROR.SERVICE_ERROR;
            responeThrowable.message = response.getMsg();
            onError(responeThrowable);
        } else {
            onError(ExceptionHandle.handleException(new JsonParseException("ParseException")));
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        ExceptionHandle.ResponeThrowable responeThrowable;
        if (e instanceof Exception) {
            //访问获得对应的Exception
            Log.d("zdddz", e.getMessage() + "/" + e.hashCode());
            responeThrowable = ExceptionHandle.handleException(e);
            if (responeThrowable.code == ExceptionHandle.UNAUTHORIZED){
                EventBus.getDefault().post(new IdentityExpiredEvent());
                return;
            }else{
                onError(responeThrowable);
            }
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
