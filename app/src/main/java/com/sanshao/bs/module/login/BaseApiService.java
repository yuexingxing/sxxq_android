package com.sanshao.bs.module.login;

import com.exam.commonbiz.net.BaseResponse;
import com.sanshao.bs.module.login.bean.LoginBean;
import com.sanshao.bs.module.login.bean.WhetherBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public interface BaseApiService {

    //测试接口
    @GET("test")
    Observable<BaseResponse<String>> test();

    //测试接口
    @GET("/weather_mini?")
    Observable<BaseResponse<WhetherBean>> getWhether(@Query("citykey") String citykey);

    //获取验证码
    @POST("/api-uaa/validata/smsCode")
    Observable<BaseResponse> getSMSCode(@Query("mobile") String mobile,
                                      @Query("type") String type);

    //验证码登录
    @POST("/util/sms/fetch")
    Observable<BaseResponse<LoginBean>> login(@Query("mobile") String mobile,
                                              @Query("code") String code);
}
