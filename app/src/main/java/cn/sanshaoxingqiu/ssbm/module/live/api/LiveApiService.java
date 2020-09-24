package cn.sanshaoxingqiu.ssbm.module.live.api;


import com.exam.commonbiz.net.BaseResponse;

import cn.sanshaoxingqiu.ssbm.module.live.bean.LiveApplyResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LiveApiService {

    //主播认证
    @POST("live/mlive/apply")
    Observable<BaseResponse> liveApply(@Body LiveApplyRequest liveApplyRequest);

    //主播认证详情
    @GET("live/mlive/anchorDetail")
    Observable<BaseResponse<LiveApplyResponse>> getAnchorDetail();
}
