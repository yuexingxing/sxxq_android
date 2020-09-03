package cn.sanshaoxingqiu.ssbm.module.live.api;


import com.exam.commonbiz.net.BaseResponse;

import cn.sanshaoxingqiu.ssbm.module.invitation.bean.UserReferrals;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LiveApiService {

    //主播认证
    @POST("api/live/mlive/apply")
    Observable<BaseResponse> liveApply(@Body LiveApplyRequest liveApplyRequest);
}
