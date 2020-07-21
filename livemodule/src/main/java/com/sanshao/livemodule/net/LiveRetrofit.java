package com.sanshao.livemodule.net;

import android.util.Log;

//import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
//import com.ss.livemodule.http.BaseObserver;
//import com.ss.livemodule.http.BaseResponse;
//import com.ss.livemodule.http.ExceptionHandle;
//
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.schedulers.Schedulers;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author yuexingxing
 * @time 2020/6/17
 */
public class LiveRetrofit {
    private static final String TAG = LiveRetrofit.class.getSimpleName();
//    private static Retrofit retrofit;
//
//    public static Retrofit getRetrofit() {
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl("https://liveroom.qcloud.com/weapp/live_room/") //设置网络请求的Url地址
//                    .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .build();
//        }
//        return retrofit;
//    }

    public static void login(String userId, String password) {
        Log.d(TAG, "resule:" + userId);
//        getRetrofit().create(LiveApiService.class)
//                .login(userId, password)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<String>() {
//
//                    @Override
//                    public void onStart() {
//
//                    }
//
//                    @Override
//                    public void onSuccess(BaseResponse<String> response) {
//                        Log.d(TAG, "onSuccess:");
//                    }
//
//                    @Override
//                    public void onError(ExceptionHandle.ResponeThrowable responeThrowable) {
//                        Log.d(TAG, "onError:"+responeThrowable.message);
//                    }
//
//                    @Override
//                    public void onFinish() {
//
//                    }
//                });
    }
}
