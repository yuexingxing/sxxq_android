package cn.sanshaoxingqiu.ssbm.module.login;

import com.exam.commonbiz.net.BaseResponse;
import cn.sanshaoxingqiu.ssbm.module.login.bean.GetCodeRequest;
import cn.sanshaoxingqiu.ssbm.module.login.bean.GetCodeResponse;
import cn.sanshaoxingqiu.ssbm.module.login.bean.LoginRequest;
import cn.sanshaoxingqiu.ssbm.module.login.bean.LoginResponse;
import cn.sanshaoxingqiu.ssbm.module.login.bean.ModifyPhoneRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public interface BaseApiService {

    //获取验证码
    @POST("w/auth/pin")
    Observable<BaseResponse<GetCodeResponse>> getSMSCode(@Body GetCodeRequest getCodeRequest);

    //验证码登录
    @POST("w/auth/appPhoneLogin")
    Observable<BaseResponse<LoginResponse>> login(@Body LoginRequest loginRequest);

    //修改手机号码
    @POST("w/meminfo/updateMemPhone")
    Observable<BaseResponse<LoginResponse>> modifyPhone(@Body ModifyPhoneRequest modifyPhoneRequest);

}
