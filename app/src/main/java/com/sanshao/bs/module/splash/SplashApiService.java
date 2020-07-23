package com.sanshao.bs.module.splash;

import com.exam.commonbiz.net.BaseResponse;
import com.sanshao.bs.module.login.bean.LoginBean;
import com.sanshao.bs.module.login.bean.WhetherBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author yuexingxing
 * @time 2020/7/23
 */
public interface SplashApiService {

    //测试接口
    @GET("/weather_mini?")
    Observable<BaseResponse<SplashInfo>> getSplashInfo(@Query("citykey") String citykey);
}
