package cn.sanshaoxingqiu.ssbm.module.login;

import com.exam.commonbiz.net.BaseResponse;

import cn.sanshaoxingqiu.ssbm.module.login.bean.GetCodeRequest;
import cn.sanshaoxingqiu.ssbm.module.login.bean.GetCodeResponse;
import cn.sanshaoxingqiu.ssbm.module.login.bean.LoginRequest;
import cn.sanshaoxingqiu.ssbm.module.login.bean.LoginResponse;
import cn.sanshaoxingqiu.ssbm.module.login.bean.ModifyPhoneRequest;

import com.exam.commonbiz.bean.UserInfo;

import cn.sanshaoxingqiu.ssbm.module.splash.VerifyApkInfo;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public interface LoginApiService {

    @GET("w/auth/getPlatParamByParamKey")
    Observable<BaseResponse<VerifyApkInfo>> getPlatParamByParamKey(@Query("group_id") String groupId,
                                                                   @Query("param_key") String paramKey);

    //获取验证码
    @POST("w/auth/pin")
    Observable<BaseResponse<GetCodeResponse>> getSMSCode(@Body GetCodeRequest getCodeRequest);

    //获取验证码
    @GET("ssxq/sms/send")
    Observable<BaseResponse<GetCodeResponse>> getSMSCode(@Query("phoneNumber") String phoneNumber,
                                                         @Query("code") String code);

    //验证码登录
    @POST("w/auth/appPhoneLogin")
    Observable<BaseResponse<LoginResponse>> login(@Body LoginRequest loginRequest);

    //修改手机号码
    @POST("w/meminfo/updateMemPhone")
    Observable<BaseResponse<LoginResponse>> modifyPhone(@Body ModifyPhoneRequest modifyPhoneRequest);

    //用邀请码获取用户信息
    @GET("w/meminfo/getMemInfoByInvitationCode")
    Observable<BaseResponse<UserInfo>> getMemInfoByInvitationCode(@Query("invitation_code") String invitationCode);

}
