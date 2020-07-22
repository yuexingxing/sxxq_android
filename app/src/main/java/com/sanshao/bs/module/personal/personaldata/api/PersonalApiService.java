package com.sanshao.bs.module.personal.personaldata.api;

import com.exam.commonbiz.net.BaseResponse;
import com.sanshao.bs.module.login.bean.LoginBean;
import com.sanshao.bs.module.login.bean.WhetherBean;
import com.sanshao.bs.module.personal.bean.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public interface PersonalApiService {

    //获取个人信息
    @GET("/util/sms/fetch")
    Observable<BaseResponse<UserInfo>> getUserInfo();

    //提交个人信息
    @POST("/util/sms/fetch")
    Observable<BaseResponse> updateUserInfo(@Body UserInfo userInfo);
}
