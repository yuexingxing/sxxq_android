package cn.sanshaoxingqiu.ssbm.module.personal.personaldata.api;

import com.exam.commonbiz.net.BaseResponse;

import com.exam.commonbiz.bean.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public interface PersonalApiService {

    //获取个人信息
    @GET("w/meminfo/getMemInfo")
    Observable<BaseResponse<UserInfo>> getUserInfo();

    //提交个人信息
    @POST("w/meminfo/updateMemInfo")
    Observable<BaseResponse> updateUserInfo(@Body UserInfo userInfo);
}
